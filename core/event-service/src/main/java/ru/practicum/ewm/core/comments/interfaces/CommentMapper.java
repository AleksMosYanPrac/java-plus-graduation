package ru.practicum.ewm.core.comments.interfaces;

import ru.practicum.ewm.core.comments.Comment;
import ru.practicum.ewm.core.api.contracts.comments.dto.CommentDto;
import ru.practicum.ewm.core.api.contracts.comments.dto.FullCommentDto;

public interface CommentMapper {
    FullCommentDto toFullCommentDto(Comment comment);

    CommentDto toCommentDto(Comment comment);
}