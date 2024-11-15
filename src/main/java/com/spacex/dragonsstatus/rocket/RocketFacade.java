package com.spacex.dragonsstatus.rocket;

import reactor.core.publisher.Mono;

public class RocketFacade {

    private final RocketHandler handler;

    public RocketFacade(RocketHandler handler) {
        this.handler = handler;
    }

    public Mono<Void> addRocket(String rocketName) {
        return handler.addRocket(rocketName);
    }

    public Mono<Void> updateStatus(String rocketName, RocketStatus newStatus) {
        return handler.updateStatus(rocketName, newStatus);
    }
}
