package com.spacex.dragonsstatus.mission;

import com.spacex.dragonsstatus.rocket.Rocket;
import reactor.core.publisher.Mono;

import java.util.Set;

public class MissionFacade {

    private final MissionHandler handler;

    public MissionFacade(MissionHandler handler) {
        this.handler = handler;
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
}
