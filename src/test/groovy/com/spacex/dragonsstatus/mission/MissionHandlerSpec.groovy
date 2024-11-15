package com.spacex.dragonsstatus.mission

import com.spacex.dragonsstatus.mission.exception.MissionNotFoundException
import com.spacex.dragonsstatus.rocket.Rocket
import com.spacex.dragonsstatus.shared.InvalidInputException
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class MissionHandlerSpec extends Specification {

    def repository = Mock(MissionRepository)

    @Subject
    MissionHandler handler = new MissionHandler(repository)

    def "should successfully add mission"() {
        given: "a valid mission name"
            String missionName = "Luna1"
            Mission savedMission = mission(missionName)

        when: "adding the mission"
            handler.addMission(missionName).block()

        then: "the mission is saved and no error occurs"
            1 * repository.save(savedMission) >> Mono.empty()

        and: "No other calls have been made"
            0 * _
    }

    def "should log error if saving mission fails"() {
        given: "a valid mission name"
            String missionName = "Artemis"
            Mission notSavedMission = mission(missionName)
            def exception = new RuntimeException("Repository error")

        when: "adding the mission"
            handler.addMission(missionName).block()

        then: "an error is thrown"
            1 * repository.save(notSavedMission) >> Mono.error(exception)
            thrown(RuntimeException)

        and: "No other calls have been made"
            0 * _
    }

    @Unroll
    def "should throw InvalidInputException for invalid mission name: #name"() {
        when: "adding a mission with an invalid name"
            handler.addMission(name).block()

        then: "an InvalidInputException is thrown"
            thrown(InvalidInputException)

        and: "No other calls have been made"
            0 * _

        where:
            name << [null, "", "   "]
    }

    def "should successfully change mission status"() {
        given: "a valid mission name and status"
            String missionName = "Double Landing"
            MissionStatus status = MissionStatus.PENDING

        when: "changing the status"
            handler.changeStatus(missionName, status).block()

        then: "the status is updated successfully"
            1 * repository.updateStatus(missionName, status) >> Mono.empty()

        and: "No other calls have been made"
            0 * _
    }

    def "should throw error if changing mission status fails"() {
        given: "a valid mission name and status"
            String missionName = "Artemis"
            MissionStatus status = MissionStatus.IN_PROGRESS
            def exception = new RuntimeException("Repository error")

        when: "changing the mission status"
            handler.changeStatus(missionName, status).block()

        then: "an error is thrown"
            1 * repository.updateStatus(missionName, status) >> Mono.error(exception)
            thrown(RuntimeException)

        and: "No other calls have been made"
            0 * _
    }

    @Unroll
    def "should throw InvalidInputException for invalid mission name or status"() {
        when: "changing status with invalid input"
            handler.changeStatus(name, status).block()

        then: "an InvalidInputException is thrown"
            thrown(InvalidInputException)

        and: "No other calls have been made"
            0 * _

        where:
            name     | status
            null     | MissionStatus.SCHEDULED
            ""       | MissionStatus.ENDED
            " "      | MissionStatus.IN_PROGRESS
            null     | MissionStatus.PENDING
            "Apollo" | null
    }

    def "should successfully assign rocket to mission"() {
        given: "a valid mission and rocket"
            String missionName = "Vertical Landing"
            String rocketName = "Falcon9"
            def rocket = new Rocket(rocketName)
            def mission = new Mission(missionName)

        when: "assigning a rocket to the mission"
            handler.assignRocket(missionName, rocket).block()

        then: "the rocket is assigned successfully"
            1 * repository.findByName(missionName) >> Mono.just(mission)
            mission.getAssignedRockets() == Set.of(rocket)

        and: "No other calls have been made"
            0 * _
    }

    @Unroll
    def "should throw InvalidInputException for invalid mission name or rocket"() {
        when: "assigning Rocket with invalid input"
            handler.assignRocket(missionName, rocket).block()

        then: "an InvalidInputException is thrown"
            thrown(InvalidInputException)

        and: "No other calls have been made"
            0 * _

        where:
            missionName         | rocket
            null                | rocket("Falcon")
            ""                  | rocket("Shiba")
            " "                 | rocket("Red Dragon")
            "Vertical Calamity" | rocket(null)
            "Test"              | rocket("")
            "Luna30"            | null
    }

    def "should throw MissionNotFoundException if mission does not exist"() {
        given: "a non-existent mission"
            String missionName = "NonExistentMission"
            String rocketName = "Falcon9"
            def rocket = new Rocket(rocketName)

        when: "assigning a rocket to a non-existent mission"
            handler.assignRocket(missionName, rocket).block()

        then: "MissionNotFoundException is thrown"
            1 * repository.findByName(missionName) >> Mono.empty()
            thrown(MissionNotFoundException)

        and: "No other calls have been made"
            0 * _
    }

    def "should successfully assign multiple rockets to mission"() {
        given: "a valid mission and a set of rockets"
            String missionName = "Apollo"
            def rocket1 = rocket("Falcon")
            def rocket2 = rocket("Dragon 2")
            Set<Rocket> rockets = Set.of(rocket1, rocket2)

        and: "a mission with assigned rocket"
            def mission = new Mission(missionName)
            mission.addRocket(rocket("Dragon 1"))

        when: "assigning multiple rockets to the mission"
            handler.assignRockets(missionName, rockets).block()

        then: "the rockets are assigned successfully"
            1 * repository.findByName(missionName) >> Mono.just(mission)
            def assignedRockets = mission.getAssignedRockets()

        and: "the mission contains previously assigned rocket and newly assigned rockets"
            assignedRockets.size() == 3
            assert assignedRockets.containsAll(rockets)

        and: "No other calls have been made"
            0 * _
    }

    def "should throw MissionNotFoundException if mission not found during bulk assign"() {
        given: "a non-existent mission"
            String missionName = "NonExistentMission"
            def rocket1 = rocket("Falcon")
            def rocket2 = rocket("Dragon 2")
            Set<Rocket> rockets = Set.of(rocket1, rocket2)

        when: "assigning multiple rockets to a non-existent mission"
            handler.assignRockets(missionName, rockets).block()

        then: "a MissionNotFoundException is thrown"
            1 * repository.findByName(missionName) >> Mono.empty()
            thrown(MissionNotFoundException)

        and: "No other calls have been made"
            0 * _
    }

    Mission mission(String name) {
        return new Mission(name)
    }

    Rocket rocket(String name) {
        return new Rocket(name)
    }
}
