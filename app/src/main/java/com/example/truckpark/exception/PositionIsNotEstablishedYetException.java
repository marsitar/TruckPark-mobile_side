package com.example.truckpark.exception;

public class PositionIsNotEstablishedYetException extends RuntimeException {
    public PositionIsNotEstablishedYetException() {
        super("Position has not been established yet.");
    }
}
