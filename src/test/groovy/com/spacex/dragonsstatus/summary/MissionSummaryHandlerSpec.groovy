package com.spacex.dragonsstatus.summary

import com.spacex.dragonsstatus.BaseITSpec
import com.spacex.dragonsstatus.mission.Mission
import com.spacex.dragonsstatus.mission.MissionStatus
import com.spacex.dragonsstatus.rocket.Rocket
import com.spacex.dragonsstatus.rocket.RocketStatus

class MissionSummaryHandlerSpec extends BaseITSpec {


    def setup() {
        cleanupMissionRepository()
    }

    def "should return sorted mission summaries with assigned rockets"() {
        given: "missions with assigned rockets"
            setupData()
        when: "getting mission summaries"
            def result = missionSummaryHandler.getMissionSummary().block()

        then: "missions should be sorted by dragonsCount descending and then by mission name descending"
            result.size() == 6
            result[0].missionName() == "Transit"
            result[1].missionName() == "Luna1"
            result[2].missionName() == "Vertical Landing"
            result[3].missionName() == "Mars"
            result[4].missionName() == "Luna2"
            result[5].missionName() == "Double Landing"

            result[0].dragonsCount() == 3
            result[1].dragonsCount() == 2
            result[2].dragonsCount() == 0
            result[3].dragonsCount() == 0
            result[4].dragonsCount() == 0
            result[5].dragonsCount() == 0
    }

    def "should handle missions without rockets"() {
        given: "a mission with no assigned rockets"
            missionRepository.save(new Mission("Lunar Mission")).block()

        when: "getting mission summaries"
            def result = missionSummaryHandler.getMissionSummary().block()

        then: "the mission summary should have no rockets"
            result.size() == 1
            result[0].missionName() == "Lunar Mission"
            result[0].dragonsCount() == 0
            result[0].rockets().isEmpty()
    }

    def "should return an empty list when there are no missions"() {
        when: "getting mission summaries with no missions in the repository"
            def result = missionSummaryHandler.getMissionSummary().block()

        then: "the result should be an empty list"
            result.isEmpty()
    }

    def "should return summary with assigned rockets"() {
        given: "a mission with assigned rockets"
            def rocket1 = new Rocket("Apollo")
            def rocket2 = new Rocket("Gemini")
            def mission = mission("Exploration Mission", MissionStatus.SCHEDULED)
            mission.addRockets(Set.of(rocket1, rocket2))
            missionRepository.save(mission).block()

        when: "getting mission summaries"
            def result = missionSummaryHandler.getMissionSummary().block()

        then: "the mission summary"
            result.size() == 1
            result[0].missionName() == "Exploration Mission"
            result[0].dragonsCount() == 2
            result[0].rockets().size() == 2
            result[0].rockets()*.rocketName().containsAll(["Apollo", "Gemini"])
    }

    Rocket rocket(String name, RocketStatus status) {
        def rocket = new Rocket(name)
        rocket.status = status
        return rocket
    }

    Mission mission(String name, MissionStatus status) {
        def mission = new Mission(name)
        mission.status = status
        return mission
    }

    def setupData() {
        // Mars mission data
        def missionMars = mission("Mars", MissionStatus.SCHEDULED)

        // Luna1 mission data
        def rocket1Luna1 = rocket("Dragon 1", RocketStatus.ON_GROUND)
        def rocket2Luna1 = rocket("Dragon 2", RocketStatus.ON_GROUND)
        def missionLuna1 = mission("Luna1", MissionStatus.PENDING)
        missionLuna1.addRockets(Set.of(rocket1Luna1, rocket2Luna1))

        // Double Landing mission data
        def missionDoubleLanding = mission("Double Landing", MissionStatus.ENDED)

        // Transit mission data
        def rocket1Transit = rocket("Red Dragon", RocketStatus.ON_GROUND)
        def rocket2Transit = rocket("Dragon XL", RocketStatus.IN_SPACE)
        def rocket3Transit = rocket("Falcon Heavy", RocketStatus.IN_SPACE)
        def missionTransit = mission("Transit", MissionStatus.IN_PROGRESS)
        missionTransit.addRockets(Set.of(rocket1Transit, rocket2Transit, rocket3Transit))

        // Luna2 mission data
        def missionLuna2 = mission("Luna2", MissionStatus.SCHEDULED)

        // Vertical Landing mission data
        def missionVerticalLanding = mission("Vertical Landing", MissionStatus.ENDED)

        missionRepository.save(missionMars)
        missionRepository.save(missionLuna1)
        missionRepository.save(missionDoubleLanding)
        missionRepository.save(missionTransit)
        missionRepository.save(missionLuna2)
        missionRepository.save(missionVerticalLanding)
    }
}
