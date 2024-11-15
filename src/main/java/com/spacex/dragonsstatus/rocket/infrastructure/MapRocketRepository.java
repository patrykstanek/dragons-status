package com.spacex.dragonsstatus.rocket.infrastructure;

import com.spacex.dragonsstatus.rocket.Rocket;
import com.spacex.dragonsstatus.rocket.RocketRepository;
import com.spacex.dragonsstatus.rocket.RocketStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class MapRocketRepository implements RocketRepository {

    private final Map<String, Rocket> rockets;

    public MapRocketRepository() {
        this.rockets = new ConcurrentHashMap<>();
    }

    @Override
    public Mono<Void> save(Rocket rocket) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> updateStatus(String name, RocketStatus newStatus) {
        return Mono.empty();
    }

    @Override
    public Flux<Rocket> findAll() {
        return Flux.defer(() ->
                Flux.fromIterable(rockets.values())
        );
    }
}
