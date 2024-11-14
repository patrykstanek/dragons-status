package com.spacex.dragonsstatus.mission;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Mission {

    private final String name;
    private MissionStatus status;
    private final Set<String> rocketsNames;

    public Mission(String name) {
        this.name = name;
        this.status = MissionStatus.SCHEDULED;
        this.rocketsNames = Collections.synchronizedSet(new HashSet<>());
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

    public Set<String> getRocketsNames() {
        synchronized (rocketsNames) {
            return Set.copyOf(rocketsNames);
        }
    }

    public void addRocket(String rocketName) {
        rocketsNames.add(rocketName);
    }

    public void addRockets(Set<String> rocketNames) {
        rocketsNames.addAll(rocketNames);
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
