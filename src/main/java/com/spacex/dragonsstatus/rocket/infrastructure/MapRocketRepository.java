package com.spacex.dragonsstatus.rocket.infrastructure;

import com.spacex.dragonsstatus.rocket.Rocket;
import com.spacex.dragonsstatus.rocket.RocketRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class MapRocketRepository implements RocketRepository {

    private final Map<String, Rocket> rockets;

    public MapRocketRepository() {
        this.rockets = new ConcurrentHashMap<>();
    }
}
