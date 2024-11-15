package com.spacex.dragonsstatus.rocket.infrastructure;

import com.spacex.dragonsstatus.rocket.Rocket;
import com.spacex.dragonsstatus.rocket.RocketRepository;
import com.spacex.dragonsstatus.rocket.RocketStatus;
import com.spacex.dragonsstatus.rocket.exception.RocketAlreadyExistException;
import com.spacex.dragonsstatus.rocket.exception.RocketNotFoundException;
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
        Rocket existingRocket = rockets.computeIfAbsent(rocket.getName(), key -> rocket);

        if (existingRocket != rocket) {
            return Mono.error(new RocketAlreadyExistException("Rocket with the name = %s already exists".formatted(rocket.getName())));
        }

        return Mono.empty();
    }

    @Override
    public Mono<Void> updateStatus(String name, RocketStatus newStatus) {
        Rocket updatedRocket = rockets.computeIfPresent(name, (key, rocket) -> {
            rocket.setStatus(newStatus);
            return rocket;
        });

        if (updatedRocket == null) {
            return Mono.error(new RocketNotFoundException("No rocket found for name = %s".formatted(name)));
        }

        return Mono.empty();
    }

    @Override
    public Mono<Rocket> findByName(String name) {
        return Mono.justOrEmpty(rockets.get(name));
    }

    @Override
    public Flux<Rocket> findAll() {
        return Flux.defer(() ->
                Flux.fromIterable(rockets.values())
        );
    }

    @Override
    public Mono<Void> clear() {
        rockets.clear();
        return Mono.empty();
    }
}
