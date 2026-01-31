package ru.practicum.ewm.core.api.contracts.comments;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.comments.dto.CommentDto;
import ru.practicum.ewm.core.api.contracts.comments.dto.FullCommentDto;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

@RequestMapping("/users/{userId}/comments")
public interface CommentPrivateContract {

    @PostMapping
    FullCommentDto create(@PathVariable Long userId,
                          @Valid @RequestBody CommentDto newCommentDto) throws NotFoundException;

    @PatchMapping("/{commentId}")
    FullCommentDto update(@PathVariable Long userId,
                          @PathVariable Long commentId,
                          @Valid @RequestBody CommentDto updatedCommentDto)
            throws NotFoundException, DataIntegrityViolation;

    @DeleteMapping("/{commentId}")
    void deleteOwnComment(@PathVariable Long userId,
                          @PathVariable Long commentId) throws NotFoundException;
}