package com.spacex.dragonsstatus.mission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import shared.InvalidInputException;

import static org.springframework.util.StringUtils.hasText;

@Service
class MissionHandler {

    private static final Logger log = LoggerFactory.getLogger(MissionHandler.class);
    private final MissionRepository repository;

    public MissionHandler(MissionRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> addMission(String name) {
        validateName(name);
        return repository.save(buildMission(name))
                .doOnError(error -> log.error("Error during adding mission named = %s".formatted(name), error))
                .doOnSuccess(aVoid -> log.debug("Successfully added mission named = %s".formatted(name)));
    }

    private void validateName(String name) {
        if (!hasText(name)) {
            throw new InvalidInputException("Mission name cannot be null or empty.");
        }
    }

    private Mission buildMission(String name) {
        return new Mission(name);
    }
}
