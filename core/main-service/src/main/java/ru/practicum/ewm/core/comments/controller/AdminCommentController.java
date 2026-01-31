package ru.practicum.ewm.core.comments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.comments.dto.CommentShort;
import ru.practicum.ewm.core.comments.interfaces.CommentService;
import ru.practicum.ewm.stats.exceptions.ApiErrorHandler;
import ru.practicum.ewm.stats.exceptions.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController implements ApiErrorHandler {

    private final CommentService service;

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) throws NotFoundException {
        service.deleteCommentById(commentId);
    }

    @GetMapping("/{eventId}")
    public List<CommentShort> getCommentsForEvent(@PathVariable Long eventId) {
        return service.getCommentsForEvent(eventId);
    }
}