package com.spacex.dragonsstatus

import com.spacex.dragonsstatus.mission.MissionConfig
import com.spacex.dragonsstatus.mission.infrastructure.MissionRepositoryConfig
import com.spacex.dragonsstatus.rocket.RocketConfig
import com.spacex.dragonsstatus.rocket.RocketFacade
import com.spacex.dragonsstatus.rocket.RocketRepository
import com.spacex.dragonsstatus.rocket.infrastructure.RocketRepositoryConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = [RocketConfig, RocketRepositoryConfig, MissionConfig, MissionRepositoryConfig])
class BaseITSpec extends Specification {

    @Autowired
    protected RocketRepository rocketRepository

    @Autowired
    protected RocketFacade rocketFacade

}
