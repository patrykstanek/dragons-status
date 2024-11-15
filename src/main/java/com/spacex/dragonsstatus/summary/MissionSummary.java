package com.spacex.dragonsstatus.summary;

import com.spacex.dragonsstatus.mission.MissionStatus;
import com.spacex.dragonsstatus.rocket.RocketStatus;

import java.util.List;

public record MissionSummary(
        String missionName,
        MissionStatus missionStatus,
        Integer dragonsCount,
        List<Rocket> rockets
) {
    public record Rocket(String rocketName, RocketStatus rocketStatus) {
    }
}
