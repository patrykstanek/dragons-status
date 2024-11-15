package com.spacex.dragonsstatus.mission;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MissionRepository {

    Mono<Void> save(Mission mission);

    Mono<Void> updateStatus(String name, MissionStatus newStatus);

    Mono<Mission> findByName(String name);

    Flux<Mission> findAll();

    Mono<Void> clear();
}
