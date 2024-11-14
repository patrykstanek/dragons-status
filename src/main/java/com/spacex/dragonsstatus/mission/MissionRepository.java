package com.spacex.dragonsstatus.mission;

import reactor.core.publisher.Mono;

public interface MissionRepository {

    Mono<Void> save(Mission mission);
}
