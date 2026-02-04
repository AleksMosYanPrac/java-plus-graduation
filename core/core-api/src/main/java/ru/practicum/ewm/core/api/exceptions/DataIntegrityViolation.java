package ru.practicum.ewm.core.api.exceptions;

public class DataIntegrityViolation extends RuntimeException {
    public DataIntegrityViolation(String s) {
        super(s);
    }
}