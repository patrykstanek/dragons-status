package com.spacex.dragonsstatus.mission.infrastructure;

import com.spacex.dragonsstatus.mission.MissionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MissionRepositoryConfig {

    @Bean
    MissionRepository missionRepository() {
        return new MapMissionRepository();
    }
}
