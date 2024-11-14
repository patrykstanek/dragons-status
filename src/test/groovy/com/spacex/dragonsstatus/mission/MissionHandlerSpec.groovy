package com.spacex.dragonsstatus.mission

import reactor.core.publisher.Mono
import shared.InvalidInputException
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

    Mission mission(String name) {
        return new Mission(name)
    }
}
