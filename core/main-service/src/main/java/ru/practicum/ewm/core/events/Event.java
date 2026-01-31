package ru.practicum.ewm.core.events;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.core.categories.Category;
import ru.practicum.ewm.core.events.dto.State;
import ru.practicum.ewm.core.users.User;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Length(min = 20, max = 2000)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Length(min = 20, max = 7000)
    private String description;
    @Column(name = "event_date")
    @Future
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    private Boolean paid;
    @Column(name = "participant_limit")
    @PositiveOrZero
    private Integer participantLimit;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Length(min = 3, max = 120)
    private String title;
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User initiator;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Enumerated(STRING)
    private State state;
    private Long views;
}