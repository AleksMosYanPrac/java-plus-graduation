package ru.practicum.ewm.core.comments.interfaces;

import ru.practicum.ewm.core.comments.Comment;
import ru.practicum.ewm.core.comments.dto.CommentDto;
import ru.practicum.ewm.core.comments.dto.FullCommentDto;

public interface CommentMapper {
    FullCommentDto toFullCommentDto(Comment comment);

    CommentDto toCommentDto(Comment comment);
}