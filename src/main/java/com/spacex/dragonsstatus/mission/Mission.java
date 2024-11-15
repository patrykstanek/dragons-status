package com.spacex.dragonsstatus.mission;

import com.spacex.dragonsstatus.rocket.Rocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Mission {

    private final String name;
    private MissionStatus status;
    private final Set<Rocket> assignedRockets;

    public Mission(String name) {
        this.name = name;
        this.status = MissionStatus.SCHEDULED;
        this.assignedRockets = Collections.synchronizedSet(new HashSet<>());
    }

    public String getName() {
        return name;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public Set<Rocket> getAssignedRockets() {
        synchronized (assignedRockets) {
            return Set.copyOf(assignedRockets);
        }
    }

    public void addRocket(Rocket rocket) {
        assignedRockets.add(rocket);
    }

    public void addRockets(Set<Rocket> rockets) {
        assignedRockets.addAll(rockets);
    }

    @Override
    public String toString() {
        return "Mission{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", rocketsNames=" + assignedRockets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return Objects.equals(name, mission.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
