package com.spacex.dragonsstatus.rocket;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RocketRepository {

    Mono<Void> save(Rocket rocket);

    Mono<Void> updateStatus(String name, RocketStatus newStatus);

    Flux<Rocket> findAll();

    Mono<Rocket> findByName(String name);

    Mono<Void> clear();
}
