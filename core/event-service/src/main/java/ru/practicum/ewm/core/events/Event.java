package ru.practicum.ewm.core.events;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.core.api.contracts.events.dto.State;
import ru.practicum.ewm.core.categories.Category;

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
    private Long initiatorId;
    @NotBlank
    @Length(min = 2, max = 250)
    private String initiatorName;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Enumerated(STRING)
    private State state;
    private Long views;

    public boolean isModerationOff() {
        return !requestModeration;
    }

    public boolean hasNoLimitForParticipants() {
        return participantLimit - confirmedRequests == 0;
    }

    public boolean addConfirmRequest() {
        if (participantLimit - (confirmedRequests + 1) >= 0) {
            confirmedRequests++;
            return true;
        } else {
            return false;
        }
    }
}