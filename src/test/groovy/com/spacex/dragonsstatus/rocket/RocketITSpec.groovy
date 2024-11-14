package com.spacex.dragonsstatus.rocket

import com.spacex.dragonsstatus.BaseITSpec

class RocketITSpec extends BaseITSpec {

    def "should add rocket"() {
        given: "a rocket name"
            String rocketName = "Dragon"

        when: "adding the rocket"
            rocketFacade.addRocket(rocketName)

        then: "the rocket should be added successfully"
            // Add assertion here to verify the result
            noExceptionThrown()
    }
}
