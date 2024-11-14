package com.spacex.dragonsstatus.mission;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MissionConfig {

    @Bean
    MissionHandler missionHandler(MissionRepository missionRepository) {
        return new MissionHandler(missionRepository);
    }

    @Bean
    MissionFacade missionFacade(MissionHandler missionHandler) {
        return new MissionFacade(missionHandler);
    }
}
