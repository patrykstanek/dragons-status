package com.spacex.dragonsstatus.rocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RocketConfig {

    @Bean
    RocketHandler rocketHandler(RocketRepository rocketRepository) {
        return new RocketHandler(rocketRepository);
    }

    @Bean
    RocketFacade rocketFacade(RocketHandler rocketHandler) {
        return new RocketFacade(rocketHandler);
    }
}
