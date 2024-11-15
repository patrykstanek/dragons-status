package com.spacex.dragonsstatus.rocket

import com.spacex.dragonsstatus.BaseITSpec

class RocketITSpec extends BaseITSpec {

    RocketStatus INITIAL_ROCKET_STATUS = RocketStatus.ON_GROUND

    def "should add a new rocket successfully"() { //TODO: implement save in DB
        given: "a new rocket is added"
            def rocketName = "Falcon"

        when: "listing all rockets"
            rocketFacade.addRocket(rocketName).block()

        then: "the rocket should be added successfully"
            noExceptionThrown()

            def result = rocketRepository.findAll().collectList().block()
            result.size() == 1
            verifyAll(result.getFirst()) {
                it.name == rocketName
                it.status == INITIAL_ROCKET_STATUS
            }
    }

    def "should not add duplicate rockets"() {
        given: "a rocket already exists"
            def rocketName = "Falcon"
            rocketFacade.addRocket(rocketName).block()

        when: "trying to add a rocket with the already existing name"
            rocketFacade.addRocket(rocketName).block()

        then: "an error should be thrown indicating a duplicate"
            thrown(RuntimeException) // TODO: implement
    }

}
