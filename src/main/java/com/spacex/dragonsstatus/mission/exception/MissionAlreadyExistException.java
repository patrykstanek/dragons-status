package com.spacex.dragonsstatus.mission.exception;

public class MissionAlreadyExistException extends RuntimeException {
    public MissionAlreadyExistException(String message) {
        super(message);
    }
}
