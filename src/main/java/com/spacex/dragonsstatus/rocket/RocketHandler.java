package com.spacex.dragonsstatus.rocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import shared.InvalidInputException;

class RocketHandler {

    private static final Logger log = LoggerFactory.getLogger(RocketHandler.class);

    private final RocketRepository repository;

    public RocketHandler(RocketRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> addRocket(String name) {
        return validateName(name)
                .map(this::buildRocket)
                .flatMap(repository::save)
                .doOnError(error -> log.error("Error during adding rocket named = %s".formatted(name), error))
                .doOnSuccess(aVoid -> log.debug("Successfully added rocket named = %s".formatted(name)));
    }

    public Mono<Void> updateStatus(String name, RocketStatus status) {
        return validateName(name)
                .flatMap(validName -> {
                    validateStatus(status);
                    return repository.updateStatus(name, status);
                })
                .doOnError(error -> log.error("Error during changing status of rocket named = %s".formatted(name), error))
                .doOnSuccess(aVoid -> log.debug("Successfully changed the status of rocket named = %s".formatted(name)));
    }

    private void validateStatus(RocketStatus status) {
        if (status == null) {
            throw new InvalidInputException("Rocket status must not be null");
        }
    }

    public Mono<String> validateName(String name) {
        return Mono.justOrEmpty(name)
                .filter(StringUtils::hasText)
                .switchIfEmpty(Mono.error(new InvalidInputException("Rocket name cannot be null or empty.")));
    }

    private Rocket buildRocket(String name) {
        return new Rocket(name);
    }
}
