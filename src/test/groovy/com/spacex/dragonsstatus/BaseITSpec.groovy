package com.spacex.dragonsstatus

import com.spacex.dragonsstatus.mission.MissionConfig
import com.spacex.dragonsstatus.mission.MissionFacade
import com.spacex.dragonsstatus.mission.MissionRepository
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

    @Autowired
    protected MissionRepository missionRepository

    @Autowired
    protected MissionFacade missionFacade


    protected void cleanupRocketRepository() {
        rocketRepository.clear().block()
    }

    protected void cleanupMissionRepository() {
        missionRepository.clear().block()
    }
}
