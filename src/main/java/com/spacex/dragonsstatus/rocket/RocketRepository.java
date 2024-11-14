package com.spacex.dragonsstatus.rocket;

import reactor.core.publisher.Mono;

public interface RocketRepository {

    Mono<Void> save(Rocket rocket);
}
