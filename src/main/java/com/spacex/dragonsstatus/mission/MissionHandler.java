package com.spacex.dragonsstatus.mission;

import com.spacex.dragonsstatus.mission.exception.MissionNotFoundException;
import com.spacex.dragonsstatus.rocket.Rocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import shared.InvalidInputException;

import java.util.Set;

import static org.springframework.util.StringUtils.hasText;

@Service
class MissionHandler {

    private static final Logger log = LoggerFactory.getLogger(MissionHandler.class);
    private final MissionRepository repository;

    public MissionHandler(MissionRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> addMission(String name) {
        return validateName(name)
                .map(this::buildMission)
                .flatMap(repository::save)
                .doOnError(error -> log.error("Error during adding mission named = %s".formatted(name), error))
                .doOnSuccess(aVoid -> log.debug("Successfully added mission named = %s".formatted(name)));
    }

    public Mono<Void> changeStatus(String name, MissionStatus status) {
        return validateName(name)
                .flatMap(validName -> {
                    validateStatus(status);
                    return repository.updateStatus(validName, status);
                })
                .doOnError(error -> log.error("Error during changing status of rocket named = %s".formatted(name), error))
                .doOnSuccess(aVoid -> log.debug("Successfully changed the status of rocket named = %s".formatted(name)));
    }

    public Mono<Void> assignRocket(String missionName, Rocket rocket) {
        return validateName(missionName)
                .flatMap(validName -> {
                    validateRocket(rocket);
                    return repository.findByName(missionName);
                })
                .switchIfEmpty(Mono.error(new MissionNotFoundException("No mission found for name = %s".formatted(missionName))))
                .doOnNext(mission -> mission.addRocket(rocket))
                .then();
    }

    public Mono<Void> assignRockets(String missionName, Set<Rocket> rockets) {
        return validateName(missionName)
                .flatMap(repository::findByName)
                .switchIfEmpty(Mono.error(new MissionNotFoundException("No mission found for name = %s".formatted(missionName))))
                .doOnNext(it -> it.addRockets(rockets))
                .then();
    }

    public Mono<String> validateName(String name) {
        return Mono.justOrEmpty(name)
                .filter(StringUtils::hasText)
                .switchIfEmpty(Mono.error(new InvalidInputException("Mission name cannot be null or empty.")));
    }

    private void validateStatus(MissionStatus status) {
        if (status == null) {
            throw new InvalidInputException("Mission status must not be null");
        }
    }

    private void validateRocket(Rocket rocket) {
        if (rocket == null || !hasText(rocket.getName())) {
            throw new InvalidInputException("Rocket cannot be null or have an empty name.");
        }
    }

    private Mission buildMission(String name) {
        return new Mission(name);
    }
}
