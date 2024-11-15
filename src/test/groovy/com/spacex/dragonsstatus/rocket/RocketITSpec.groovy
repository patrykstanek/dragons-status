package com.spacex.dragonsstatus.rocket

import com.spacex.dragonsstatus.BaseITSpec
import com.spacex.dragonsstatus.rocket.exception.RocketAlreadyExistException
import com.spacex.dragonsstatus.rocket.exception.RocketNotFoundException
import shared.InvalidInputException

class RocketITSpec extends BaseITSpec {

    def setup() {
        cleanupRocketRepository()
        rocketRepository.save(new Rocket("Falcon9")).block()
        rocketRepository.save(new Rocket("Dragon B")).block()
    }

    def "should add a new rocket successfully"() {
        given: "a new rocket is added"
            def rocketName = "Falcon"

        when: "listing all rockets"
            rocketFacade.addRocket(rocketName).block()

        then: "the rocket should be added successfully"
            noExceptionThrown()

            def result = rocketRepository.findAll().collectList().block()
            result.size() == 3
            result.find() {
                it.name == rocketName
            }
    }

    def "should not add duplicate rockets"() {
        given: "a rocket already exists"
            def rocketName = "Dragon B"

        when: "trying to add a rocket with the already existing name"
            rocketFacade.addRocket(rocketName).block()

        then: "an error should be thrown indicating a duplicate"
            thrown(RocketAlreadyExistException)
    }

    def "should find all added rockets"() {
        when: "listing of all rockets"
            def result = rocketRepository.findAll().collectList().block()

        then: "the rocket should be found successfully"
            result.size() == 2
            result*.name.containsAll(["Falcon9", "Dragon B"])
    }

    def "should successfully update the status of an existing rocket"() {
        given: "a valid rocket name and a new status"
            String rocketName = "Falcon9"
            RocketStatus newStatus = RocketStatus.IN_SPACE

        when: "updating the rocket status"
            rocketFacade.updateStatus(rocketName, newStatus).block()

        then: "the status is updated successfully"
            def updatedRocket = rocketRepository.findByName(rocketName).block()
            assert updatedRocket != null
            assert updatedRocket.status == RocketStatus.IN_SPACE
    }

    def "should return error if rocket does not exist"() {
        given: "a non-existent rocket name"
            String nonExistentRocket = "UnknownRocket"

        when: "trying to update the status"
            rocketFacade.updateStatus(nonExistentRocket, RocketStatus.IN_SPACE).block()

        then: "an error should be thrown indicating lack of corresponding rocket"
            thrown(RocketNotFoundException)
    }

    def "should return error for null rocket name"() {
        when: "updating status with null rocket name"
            rocketFacade.updateStatus(null, RocketStatus.IN_REPAIR).block()

        then: "an InvalidInputException is thrown"
            def ex = thrown(InvalidInputException)
            ex.message == "Rocket name cannot be null or empty."
    }

    def "should return error for null rocket status"() {
        given: "a valid rocket name but a null status"
            String rocketName = "Falcon9"

        when: "trying to update the status with null status"
            rocketFacade.updateStatus(rocketName, null).block()

        then: "an InvalidInputException is thrown"
            def ex = thrown(InvalidInputException)
            ex.message == "Rocket status must not be null"
    }
}
