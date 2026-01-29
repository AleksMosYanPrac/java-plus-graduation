package ru.practicum.ewm.core.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.core.comments.dto.CommentShort;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByIdAndAuthorId(Long commentId, Long userId);

    List<CommentShort> getCommentsByEventId(Long eventId);
}