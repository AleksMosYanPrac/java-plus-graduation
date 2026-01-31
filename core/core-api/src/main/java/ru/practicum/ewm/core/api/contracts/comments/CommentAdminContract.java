package ru.practicum.ewm.core.api.contracts.comments;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.ewm.core.api.contracts.comments.dto.CommentShort;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

import java.util.List;

@RequestMapping("/admin/comments")
public interface CommentAdminContract {

    @DeleteMapping("/{commentId}")
    void deleteComment(@PathVariable Long commentId) throws NotFoundException;

    @GetMapping("/{eventId}")
    List<CommentShort> getCommentsForEvent(@PathVariable Long eventId);
}