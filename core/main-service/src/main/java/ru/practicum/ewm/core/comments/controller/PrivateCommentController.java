package ru.practicum.ewm.core.comments.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.comments.dto.CommentDto;
import ru.practicum.ewm.core.comments.dto.FullCommentDto;
import ru.practicum.ewm.core.comments.interfaces.CommentService;
import ru.practicum.ewm.core.exceptions.ApiErrorHandler;
import ru.practicum.ewm.core.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.exceptions.NotFoundException;

@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
public class PrivateCommentController implements ApiErrorHandler {

        private final CommentService service;

        @PostMapping
        public FullCommentDto create(@PathVariable Long userId,
                                     @Valid @RequestBody CommentDto newCommentDto) throws NotFoundException {
            return service.addCommentToEventByUser(userId, newCommentDto);
        }

        @PatchMapping("/{commentId}")
        public FullCommentDto update(@PathVariable Long userId,
                                     @PathVariable Long commentId,
                                     @Valid @RequestBody CommentDto updatedCommentDto)
                throws NotFoundException, DataIntegrityViolation {
            return service.updateCommentByUser(userId, commentId, updatedCommentDto);
        }

        @DeleteMapping("/{commentId}")
        public void deleteOwnComment(@PathVariable Long userId,
                                     @PathVariable Long commentId) throws NotFoundException {
            service.deleteOwnComment(userId, commentId);
        }
}