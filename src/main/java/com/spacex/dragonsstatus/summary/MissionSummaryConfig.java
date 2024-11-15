package com.spacex.dragonsstatus.summary;

import com.spacex.dragonsstatus.mission.MissionFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MissionSummaryConfig {

    @Bean
    MissionSummaryHandler missionSummaryHandler(MissionFacade missionFacade) {
        return new MissionSummaryHandler(missionFacade);
    }
}
