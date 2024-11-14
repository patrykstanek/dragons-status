package com.spacex.dragonsstatus.rocket.infrastructure;

import com.spacex.dragonsstatus.rocket.RocketRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RocketRepositoryConfig {

    @Bean
    RocketRepository rocketRepository() {
        return new MapRocketRepository();
    }
}
