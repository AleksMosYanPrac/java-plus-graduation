package ru.practicum.ewm.core.events;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.core.api.contracts.events.dto.Sort;
import ru.practicum.ewm.core.api.contracts.events.dto.State;
import ru.practicum.ewm.core.api.validation.DatableRange;
import ru.practicum.ewm.core.api.validation.DateRange;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Data
@DateRange
public class Filter implements DatableRange {
    private Long[] users;
    private String[] states;
    private Integer[] categories;
    private String text;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private Sort sort;
    private int from;
    private int size;

    public Filter(Long[] users,
                  String[] states,
                  Integer[] categories,
                  LocalDateTime rangeStart,
                  LocalDateTime rangeEnd,
                  int from,
                  int size) {
        this.users = users;
        this.states = states;
        this.categories = categories;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.from = from;
        this.size = size;
    }

    public Filter(String text,
                  Integer[] categories,
                  Boolean paid,
                  LocalDateTime rangeStart,
                  LocalDateTime rangeEnd,
                  Boolean onlyAvailable,
                  Sort sort,
                  int from,
                  int size) {
        this.text = text;
        this.categories = categories;
        this.paid = paid;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.onlyAvailable = onlyAvailable;
        this.sort = sort;
        this.from = from;
        this.size = size;
    }

    public BooleanBuilder getAdminPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        if (Objects.nonNull(users)) {
            builder.and(QEvent.event.initiatorId.in(users));
        }
        if (Objects.nonNull(states)) {
            builder.and(QEvent.event.state.in(Arrays.stream(states).map(State::valueOf).toList()));
        }
        if (Objects.isNull(rangeStart)) {
            rangeStart = LocalDateTime.now();
            if (Objects.nonNull(rangeEnd)) {
                builder.and(QEvent.event.eventDate.between(rangeStart, rangeEnd));
            } else {
                builder.and(QEvent.event.eventDate.goe(rangeStart));
            }
        } else {
            builder.and(QEvent.event.eventDate.goe(rangeStart));
        }
        if (Objects.nonNull(categories)) {
            builder.and(QEvent.event.category.id.in(categories));
        }
        return builder;
    }

    public BooleanBuilder getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QEvent.event.state.eq(State.PUBLISHED));
        if (Objects.nonNull(text)) {
            BooleanExpression byAnnotation = QEvent.event.annotation.containsIgnoreCase(text);
            BooleanExpression byDescription = QEvent.event.description.containsIgnoreCase(text);
            builder.and(byAnnotation.or(byDescription));
        }
        if (Objects.nonNull(paid)) {
            builder.and(QEvent.event.paid.eq(paid));
        }
        if (Objects.nonNull(categories) && categories.length > 0) {
            builder.and(QEvent.event.category.id.in(categories));
        }
        if (Boolean.TRUE.equals(onlyAvailable)) {
            builder.and(QEvent.event.participantLimit.gt(QEvent.event.confirmedRequests));
        }
        if (Objects.isNull(rangeStart)) {
            rangeStart = LocalDateTime.now();
            if (Objects.nonNull(rangeEnd)) {
                builder.and(QEvent.event.eventDate.between(rangeStart, rangeEnd));
            } else {
                builder.and(QEvent.event.eventDate.goe(rangeStart));
            }
        } else {
            builder.and(QEvent.event.eventDate.goe(rangeStart));
        }
        return builder;
    }

    public Pageable getPage() {
        return PageRequest.of(from, size);
    }

    public Pageable getSortedPage(org.springframework.data.domain.Sort.Direction direction, String... properties) {
        return PageRequest.of(from, size, direction, properties);
    }
}