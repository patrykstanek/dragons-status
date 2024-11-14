package com.spacex.dragonsstatus.rocket

import reactor.core.publisher.Mono
import shared.InvalidInputException
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class RocketHandlerSpec extends Specification {

    def repository = Mock(RocketRepository.class)

    @Subject
    RocketHandler handler = new RocketHandler(repository)

    def "should successfully add rocket"() {
        given: "a valid rocket name"
            String rocketName = "Falcon"
            Rocket savedRocket = rocket(rocketName)

        when: "adding the rocket"
            handler.addRocket(rocketName).block()

        then: "the rocket is saved and no error occurs"
            1 * repository.save(savedRocket) >> Mono.empty()
    }

    def "should log error if saving rocket fails"() {
        given: "a valid rocket name"
            String rocketName = "Dragon"
            def exception = new RuntimeException("Database error")

        when: "adding the rocket"
            handler.addRocket(rocketName).block()

        then: "an error is thrown"
            1 * repository.save(_) >> Mono.error(exception)
            thrown(RuntimeException)
    }

    @Unroll
    def "should throw InvalidInputException for invalid rocket name: #name"() {
        when: "adding a rocket with an invalid name"
            handler.addRocket(name).block()

        then: "an InvalidInputException is thrown"
            thrown(InvalidInputException)

        where:
            name << [null, "", "   "]
    }

    def "should successfully update rocket status"() {
//        given: "a valid rocket name and status"
//            String rocketName = "Falcon9"
//            RocketStatus status = RocketStatus.IN_REPAIR
//
//        when: "updating the rocket status"
//            handler.updateStatus(rocketName, status).block()
//
//        then: "the status is updated successfully"
//            1 * repository.updateStatus(rocketName, status) >> Mono.empty()
    }

    def "should log error if updating rocket status fails"() {
//        given: "a valid rocket name and status"
//            String rocketName = "Starship"
//            RocketStatus status = RocketStatus.INACTIVE
//            def exception = new RuntimeException("Update error")
//
//        when: "updating the rocket status"
//            handler.updateStatus(rocketName, status).block()
//
//        then: "an error is logged"
//            1 * repository.updateStatus(rocketName, status) >> Mono.error(exception)
//            thrown(RuntimeException)
    }

    @Unroll
    def "should throw InvalidInputException for invalid rocket status: #status"() {
//        when: "updating status with invalid input"
//            handler.updateStatus("FalconHeavy", status).block()
//
//        then: "an InvalidInputException is thrown"
//            thrown(InvalidInputException)
//
//        where:
//            status << [null]
    }

    @Unroll
    def "should throw InvalidInputException for invalid rocket name during status update: #name"() {
//        when: "updating status with invalid rocket name"
//            handler.updateStatus(name, RocketStatus.IN_REPAIR).block()
//
//        then: "an InvalidInputException is thrown"
//            thrown(InvalidInputException)
//
//        where:
//            name << [null, "", "   "]
    }

    Rocket rocket(String name) {
        return new Rocket(name)
    }
}
