package com.spacex.dragonsstatus.rocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import shared.InvalidInputException;

import static org.springframework.util.StringUtils.hasText;

class RocketHandler {

    private static final Logger log = LoggerFactory.getLogger(RocketHandler.class);

    private final RocketRepository repository;

    public RocketHandler(RocketRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> addRocket(String name) {
        validateName(name);
        return Mono.just(name)
                .flatMap(this::buildRocket)
                .flatMap(repository::save)
                .doOnError(error -> log.error("Error during adding rocket named = %s".formatted(name), error))
                .doOnSuccess(aVoid -> log.debug("Successfully added rocket named = %s".formatted(name)));
    }

    private void validateName(String name) {
        if (!hasText(name)) {
            throw new InvalidInputException("Rocket name cannot be null or empty.");
        }
    }

    private Mono<Rocket> buildRocket(String name) {
        return Mono.just(new Rocket(name));
    }
}
