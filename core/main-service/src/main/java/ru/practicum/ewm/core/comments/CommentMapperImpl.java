package ru.practicum.ewm.core.comments;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.core.comments.dto.CommentDto;
import ru.practicum.ewm.core.comments.dto.FullCommentDto;
import ru.practicum.ewm.core.comments.interfaces.CommentMapper;

@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public FullCommentDto toFullCommentDto(Comment comment) {
        FullCommentDto commentDto = new FullCommentDto();
        commentDto.setId(comment.getId());
        commentDto.setEventId(comment.getEventId());
        commentDto.setAuthorName(comment.getAuthor().getName());
        commentDto.setText(comment.getText());
        commentDto.setCreated(comment.getCreated());
        return commentDto;
    }

    @Override
    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getEventId(), comment.getText());
    }
}