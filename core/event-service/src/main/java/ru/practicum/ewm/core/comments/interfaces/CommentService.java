package ru.practicum.ewm.core.comments.interfaces;

import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.api.contracts.comments.dto.CommentDto;
import ru.practicum.ewm.core.api.contracts.comments.dto.CommentShort;
import ru.practicum.ewm.core.api.contracts.comments.dto.FullCommentDto;

import java.util.List;

public interface CommentService {
    FullCommentDto addCommentToEventByUser(Long userId, CommentDto newCommentDto) throws NotFoundException;

    FullCommentDto updateCommentByUser(Long userId, Long commentId, CommentDto updatedCommentDto) throws NotFoundException, DataIntegrityViolation;

    void deleteOwnComment(Long userId, Long commentId) throws NotFoundException;

    void deleteCommentById(Long commentId) throws NotFoundException;

    List<CommentShort> getCommentsForEvent(Long eventId);
}