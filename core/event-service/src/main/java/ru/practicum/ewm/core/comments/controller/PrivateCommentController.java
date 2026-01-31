package ru.practicum.ewm.core.comments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.comments.CommentPrivateContract;
import ru.practicum.ewm.core.api.exceptions.ApiErrorContract;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.api.contracts.comments.dto.CommentDto;
import ru.practicum.ewm.core.api.contracts.comments.dto.FullCommentDto;
import ru.practicum.ewm.core.comments.interfaces.CommentService;

@RestController
@RequiredArgsConstructor
public class PrivateCommentController implements CommentPrivateContract, ApiErrorContract {

    private final CommentService service;

    @Override
    public FullCommentDto create(Long userId, CommentDto newCommentDto) throws NotFoundException {
        return service.addCommentToEventByUser(userId, newCommentDto);
    }

    @Override
    public FullCommentDto update(Long userId, Long commentId, CommentDto updatedCommentDto)
            throws NotFoundException, DataIntegrityViolation {
        return service.updateCommentByUser(userId, commentId, updatedCommentDto);
    }

    @Override
    public void deleteOwnComment(Long userId, Long commentId) throws NotFoundException {
        service.deleteOwnComment(userId, commentId);
    }
}