package com.spacex.dragonsstatus.summary;

import com.spacex.dragonsstatus.mission.Mission;
import com.spacex.dragonsstatus.mission.MissionFacade;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

public class MissionSummaryHandler {

    private final MissionFacade missionFacade;

    public MissionSummaryHandler(MissionFacade missionFacade) {
        this.missionFacade = missionFacade;
    }

    public Mono<List<MissionSummary>> getMissionSummary() {
        return missionFacade.findAll()
                .flatMap(this::mapToMissionSummary)
                .collectSortedList(getMissionSummaryComparator());
    }

    private Mono<MissionSummary> mapToMissionSummary(Mission mission) {
        return Flux.fromIterable(mission.getAssignedRockets())
                .map(rocket -> new MissionSummary.Rocket(rocket.getName(), rocket.getStatus()))
                .collectList()
                .map(rockets -> buildMissionSummary(mission, rockets));
    }

    private Comparator<MissionSummary> getMissionSummaryComparator() {
        return Comparator
                .comparing(MissionSummary::dragonsCount, Comparator.reverseOrder())
                .thenComparing(MissionSummary::missionName, Comparator.reverseOrder());
    }

    private MissionSummary buildMissionSummary(Mission mission, List<MissionSummary.Rocket> rockets) {
        return new MissionSummary(
                mission.getName(),
                mission.getStatus(),
                rockets.size(),
                rockets
        );
    }
}
