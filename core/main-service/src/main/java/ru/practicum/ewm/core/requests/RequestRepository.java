package ru.practicum.ewm.core.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.core.events.Event;
import ru.practicum.ewm.core.users.User;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByEvent(Event event);

    List<ParticipationRequest> findByEventId(Long id);

    List<ParticipationRequest> findAllByRequesterId(Long id);

    boolean existsByEventAndRequester(Event event, User user);

    Optional<ParticipationRequest> findByIdAndRequesterId(Long requestId, Long requesterId);
}