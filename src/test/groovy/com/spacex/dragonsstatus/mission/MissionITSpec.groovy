package com.spacex.dragonsstatus.mission

import com.spacex.dragonsstatus.BaseITSpec
import com.spacex.dragonsstatus.mission.exception.MissionAlreadyExistException
import com.spacex.dragonsstatus.mission.exception.MissionNotFoundException
import com.spacex.dragonsstatus.rocket.Rocket
import shared.InvalidInputException

class MissionITSpec extends BaseITSpec {

    def setup() {
        cleanupMissionRepository()
        missionRepository.save(new Mission("Luna1")).block()
        missionRepository.save(new Mission("Vertical Calamity")).block()
    }

    def "should successfully add a new mission"() {
        given: "a mission name"
            String missionName = "Luna2"

        when: "the mission is added"
            missionFacade.addMission(missionName).block()

        then: "the mission is saved in the repository"
            def mission = missionRepository.findByName(missionName).block()
            mission != null
            mission.name == missionName
    }

    def "should throw an error when adding a mission with a duplicate name"() {
        given: "an existing mission"
            String missionName = "Vertical Calamity"

        when: "adding a mission with the same name"
            missionFacade.addMission(missionName).block()

        then: "MissionAlreadyExistException is thrown"
            missionRepository.findAll().collectList().block().size() == 2
            thrown(MissionAlreadyExistException)
    }

    def "should change the status of an existing mission"() {
        given: "a mission with default status"
            String missionName = "Luna1"

        when: "updating the mission status"
            missionFacade.changeStatus(missionName, MissionStatus.IN_PROGRESS).block()

        then: "the status is updated correctly"
            def mission = missionRepository.findByName(missionName).block()
            mission.status == MissionStatus.IN_PROGRESS
    }

    def "should throw an error when trying to update a status of an non-existing mission"() {
        given: "a non-existing mission"
            String missionName = "Absynt mission"

        when: "updating the mission status"
            missionFacade.changeStatus(missionName, MissionStatus.IN_PROGRESS).block()

        then: "MissionNotFoundException is thrown"
            thrown(MissionNotFoundException)
    }

    def "should assign a rocket to an existing mission"() {
        given: "an existing mission and a rocket"
            String missionName = "Vertical Calamity"
            Rocket rocket = new Rocket("Falcon Heavy")

        when: "assigning the rocket to the mission"
            missionFacade.assignRocket(missionName, rocket).block()

        then: "the rocket is assigned to the mission"
            def mission = missionRepository.findByName(missionName).block()
            def assignedRockets = mission.getAssignedRockets()
            assignedRockets.size() == 1
            assert assignedRockets.contains(rocket)
    }

    def "should throw an error when assigning a rocket to a non-existent mission"() {
        given: "a non-existent mission"
            String nonExistentMission = "NonExistent"
            Rocket rocket = new Rocket("Starship")

        when: "attempting to assign a rocket"
            missionFacade.assignRocket(nonExistentMission, rocket).block()

        then: "MissionNotFoundException is thrown"
            thrown(MissionNotFoundException)
    }

    def "should assign a rocket to an existing mission"() {
        given: "an existing mission and a rocket"
            String missionName = "Vertical Calamity"
            Rocket rocket1 = new Rocket("Falcon Heavy")
            Rocket rocket2 = new Rocket("Asiro")
            def rockets = Set.of(rocket1, rocket2)

        when: "assigning the rocket to the mission"
            missionFacade.assignRockets(missionName, rockets).block()

        then: "the rocket is assigned to the mission"
            def mission = missionRepository.findByName(missionName).block()
            def assignedRockets = mission.getAssignedRockets()
            assignedRockets.size() == 2
            assert assignedRockets.containsAll(rockets)
    }

    def "should throw an error when assigning a rocket to a non-existent mission"() {
        given: "a non-existent mission"
            String nonExistentMission = "NonExistent"
            Rocket rocket1 = new Rocket("Falcon Heavy")
            Rocket rocket2 = new Rocket("Asiro")
            def rockets = Set.of(rocket1, rocket2)

        when: "attempting to assign a rocket"
            missionFacade.assignRockets(nonExistentMission, rockets).block()

        then: "MissionNotFoundException is thrown"
            thrown(MissionNotFoundException)
    }

    def "should throw an error during empty input validation"() {
        when: "adding a mission with an empty name"
            missionFacade.addMission("").block()

        then: "InvalidInputException is thrown"
            def ex = thrown(InvalidInputException)
            ex.message == "Mission name cannot be null or empty."
    }

    def "should handle validation errors when updating status with null status"() {
        given: "an existing mission"
            String missionName = "Luna1"

        when: "updating the status with null"
            missionFacade.changeStatus(missionName, null).block()

        then: "InvalidInputException is thrown"
            def ex = thrown(InvalidInputException)
            ex.message == "Mission status must not be null"
    }
}
