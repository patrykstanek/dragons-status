package com.spacex.dragonsstatus.rocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketConfig {

    @Bean
    public RocketHandler rocketHandler(RocketRepository rocketRepository) {
        return new RocketHandler(rocketRepository);
    }

    @Bean
    public RocketFacade rocketFacade(RocketHandler rocketHandler) {
        return new RocketFacade(rocketHandler);
    }
}
