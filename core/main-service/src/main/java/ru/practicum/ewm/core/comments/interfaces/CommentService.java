package ru.practicum.ewm.core.comments.interfaces;

import ru.practicum.ewm.core.comments.dto.CommentDto;
import ru.practicum.ewm.core.comments.dto.CommentShort;
import ru.practicum.ewm.core.comments.dto.FullCommentDto;
import ru.practicum.ewm.core.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.exceptions.NotFoundException;

import java.util.List;

public interface CommentService {
    FullCommentDto addCommentToEventByUser(Long userId, CommentDto newCommentDto) throws NotFoundException;

    FullCommentDto updateCommentByUser(Long userId, Long commentId, CommentDto updatedCommentDto) throws NotFoundException, DataIntegrityViolation;

    void deleteOwnComment(Long userId, Long commentId) throws NotFoundException;

    void deleteCommentById(Long commentId) throws NotFoundException;

    List<CommentShort> getCommentsForEvent(Long eventId);
}