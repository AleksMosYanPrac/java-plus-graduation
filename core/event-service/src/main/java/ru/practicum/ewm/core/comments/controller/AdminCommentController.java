package ru.practicum.ewm.core.comments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.comments.CommentAdminContract;
import ru.practicum.ewm.core.api.exceptions.ApiErrorContract;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.api.contracts.comments.dto.CommentShort;
import ru.practicum.ewm.core.comments.interfaces.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminCommentController implements CommentAdminContract, ApiErrorContract {

    private final CommentService service;

    @Override
    public void deleteComment(Long commentId) throws NotFoundException {
        service.deleteCommentById(commentId);
    }

    @Override
    public List<CommentShort> getCommentsForEvent(Long eventId) {
        return service.getCommentsForEvent(eventId);
    }
}