package com.spacex.dragonsstatus.rocket.exception;

public class RocketAlreadyExistException extends RuntimeException {
    public RocketAlreadyExistException(String message) {
        super(message);
    }
}
