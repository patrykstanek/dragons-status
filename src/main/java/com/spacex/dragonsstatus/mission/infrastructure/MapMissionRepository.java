package com.spacex.dragonsstatus.mission.infrastructure;

import com.spacex.dragonsstatus.mission.Mission;
import com.spacex.dragonsstatus.mission.MissionRepository;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class MapMissionRepository implements MissionRepository {

    private final Map<String, Mission> missions;

    public MapMissionRepository() {
        this.missions = new ConcurrentHashMap<>();
    }

    @Override
    public Mono<Void> save(Mission mission) {
        return Mono.empty();
    }

}
