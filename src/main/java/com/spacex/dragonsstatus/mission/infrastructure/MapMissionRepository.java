package com.spacex.dragonsstatus.mission.infrastructure;

import com.spacex.dragonsstatus.mission.Mission;
import com.spacex.dragonsstatus.mission.MissionRepository;
import com.spacex.dragonsstatus.mission.MissionStatus;
import com.spacex.dragonsstatus.mission.exception.MissionAlreadyExistException;
import com.spacex.dragonsstatus.mission.exception.MissionNotFoundException;
import reactor.core.publisher.Flux;
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
        Mission existingRocket = missions.computeIfAbsent(mission.getName(), key -> mission);

        if (existingRocket != mission) {
            return Mono.error(new MissionAlreadyExistException("Mission with the name = %s already exists".formatted(mission.getName())));
        }

        return Mono.empty();
    }

    @Override
    public Mono<Void> updateStatus(String name, MissionStatus newStatus) {
        Mission updatedRocket = missions.computeIfPresent(name, (key, mission) -> {
            mission.setStatus(newStatus);
            return mission;
        });

        if (updatedRocket == null) {
            return Mono.error(new MissionNotFoundException("No mission found for name = %s".formatted(name)));
        }

        return Mono.empty();
    }

    @Override
    public Mono<Mission> findByName(String name) {
        return Mono.justOrEmpty(missions.get(name));
    }

    @Override
    public Flux<Mission> findAll() {
        return Flux.defer(() ->
                Flux.fromIterable(missions.values())
        );
    }

    @Override
    public Mono<Void> clear() {
        missions.clear();
        return Mono.empty();
    }

}
