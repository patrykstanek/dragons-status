package com.spacex.dragonsstatus.rocket;

import reactor.core.publisher.Mono;

public interface RocketRepository {

    Mono<Void> save(Rocket rocket);
    Mono<Void> updateStatus(String name, RocketStatus newStatus);

}
