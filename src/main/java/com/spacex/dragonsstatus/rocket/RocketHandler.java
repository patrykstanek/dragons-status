package com.spacex.dragonsstatus.rocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RocketHandler {

    private static final Logger log = LoggerFactory.getLogger(RocketHandler.class);

    private final RocketRepository repository;

    public RocketHandler(RocketRepository repository) {
        this.repository = repository;
    }

}
