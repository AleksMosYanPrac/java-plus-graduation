package ru.practicum.ewm.core.comments;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.core.comments.dto.CommentDto;
import ru.practicum.ewm.core.comments.dto.CommentShort;
import ru.practicum.ewm.core.comments.dto.FullCommentDto;
import ru.practicum.ewm.core.comments.interfaces.CommentMapper;
import ru.practicum.ewm.core.comments.interfaces.CommentService;
import ru.practicum.ewm.core.events.EventRepository;
import ru.practicum.ewm.stats.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.stats.exceptions.NotFoundException;
import ru.practicum.ewm.core.users.User;
import ru.practicum.ewm.core.users.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper mapper;

    @Override
    @Transactional
    public FullCommentDto addCommentToEventByUser(Long authorId, CommentDto newCommentDto) throws NotFoundException {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (!eventRepository.existsById(newCommentDto.getEventId())) {
            throw new NotFoundException("Событие не найдено");
        }
        Comment newComment = new Comment();
        newComment.setEventId(newCommentDto.getEventId());
        newComment.setAuthor(author);
        newComment.setText(newCommentDto.getText());
        newComment.setCreated(LocalDateTime.now());
        return mapper.toFullCommentDto(commentRepository.save(newComment));
    }

    @Override
    @Transactional
    public FullCommentDto updateCommentByUser(Long authorId, Long commentId, CommentDto updatedCommentDto)
            throws NotFoundException, DataIntegrityViolation {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий не найден"));
        if (!comment.getAuthor().getId().equals(authorId)) {
            throw new DataIntegrityViolation("Не доступно для редактирования");
        }
        comment.setEventId(updatedCommentDto.getEventId());
        comment.setText(updatedCommentDto.getText());
        return mapper.toFullCommentDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void deleteOwnComment(Long userId, Long commentId) throws NotFoundException {
        if (commentRepository.existsByIdAndAuthorId(commentId, userId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new NotFoundException("Комментарий не найден");
        }
    }

    @Override
    @Transactional
    public void deleteCommentById(Long commentId) throws NotFoundException {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new NotFoundException("Комментарий не найден");
        }
    }

    public List<CommentShort> getCommentsForEvent(Long eventId) {
        return commentRepository.getCommentsByEventId(eventId);
    }
}