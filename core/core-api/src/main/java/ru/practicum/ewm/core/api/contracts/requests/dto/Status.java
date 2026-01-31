package ru.practicum.ewm.core.api.contracts.requests.dto;

public enum Status {
    PENDING, // в ожидании
    CONFIRMED, // подтверждено
    REJECTED,// отклонено
    CANCELED
}