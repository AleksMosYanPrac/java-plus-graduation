package ru.practicum.ewm.core.requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.core.api.contracts.requests.dto.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "participation_requests")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    private Long eventId;
    private Long requesterId;
    @Enumerated(EnumType.STRING)
    private Status status;
}