package com.spacex.dragonsstatus.rocket.exception;

public class RocketNotFoundException extends RuntimeException {
    public RocketNotFoundException(String message) {
        super(message);
    }
}
