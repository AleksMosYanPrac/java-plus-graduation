package ru.practicum.ewm.core.requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.core.events.Event;
import ru.practicum.ewm.core.events.dto.State;
import ru.practicum.ewm.core.users.User;

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
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User requester;
    @Enumerated(EnumType.STRING)
    private State status;
}