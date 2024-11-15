package com.spacex.dragonsstatus.mission.exception;

public class MissionNotFoundException extends RuntimeException {
    public MissionNotFoundException(String message) {
        super(message);
    }
}
