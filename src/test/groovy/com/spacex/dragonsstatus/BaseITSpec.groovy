package com.spacex.dragonsstatus

import com.spacex.dragonsstatus.rocket.RocketConfig
import com.spacex.dragonsstatus.rocket.RocketFacade
import com.spacex.dragonsstatus.rocket.RocketRepository
import com.spacex.dragonsstatus.rocket.infrastructure.RocketRepositoryConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = [RocketConfig, RocketRepositoryConfig])
class BaseITSpec extends Specification {

    @Autowired
    protected RocketRepository rocketRepository

    @Autowired
    protected RocketFacade rocketFacade

}
