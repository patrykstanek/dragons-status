package com.spacex.dragonsstatus.mission;

import com.spacex.dragonsstatus.rocket.Rocket;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public class MissionFacade {

    private final MissionHandler handler;
    private final MissionRepository repository;

    public MissionFacade(MissionHandler handler, MissionRepository repository) {
        this.handler = handler;
        this.repository = repository;
    }

    public Mono<Void> addMission(String name) {
        return handler.addMission(name);
    }

    public Mono<Void> changeStatus(String missionName, MissionStatus newStatus) {
        return handler.changeStatus(missionName, newStatus);
    }

    public Mono<Void> assignRocket(String missionName, Rocket rocket) {
        return handler.assignRocket(missionName, rocket);
    }

    public Mono<Void> assignRockets(String missionName, Set<Rocket> rockets) {
        return handler.assignRockets(missionName, rockets);
    }

    public Flux<Mission> findAll() {
        return repository.findAll();
    }
}
