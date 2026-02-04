package ru.practicum.ewm.core.api.contracts.requests.dto;

import lombok.Data;

@Data
public class ParticipationRequestDto {
    private Long id;
    private String created;
    private Long event;
    private Long requester;
    private Status status;
}