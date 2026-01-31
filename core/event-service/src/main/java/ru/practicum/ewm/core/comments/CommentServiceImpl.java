package ru.practicum.ewm.core.comments;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import ru.practicum.ewm.core.api.contracts.users.UsersFeignClient;
import ru.practicum.ewm.core.api.contracts.users.dto.UserShortDto;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.api.contracts.comments.dto.CommentDto;
import ru.practicum.ewm.core.api.contracts.comments.dto.CommentShort;
import ru.practicum.ewm.core.api.contracts.comments.dto.FullCommentDto;
import ru.practicum.ewm.core.comments.interfaces.CommentMapper;
import ru.practicum.ewm.core.comments.interfaces.CommentService;
import ru.practicum.ewm.core.events.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UsersFeignClient usersClient;
    private final EventRepository eventRepository;
    private final CommentMapper mapper;
    private final TransactionTemplate transactionTemplate;

    @Override
    public FullCommentDto addCommentToEventByUser(Long authorId, CommentDto newCommentDto) throws NotFoundException {
        UserShortDto author = findUserById(authorId)
                .orElseThrow(() -> new NotFoundException("User not found with Id:" + authorId));
        if (!eventRepository.existsById(newCommentDto.getEventId())) {
            throw new NotFoundException("Event not found with Id:" + newCommentDto.getEventId());
        }
        return transactionTemplate.execute(status -> {
            Comment newComment = new Comment();
            newComment.setEventId(newCommentDto.getEventId());
            newComment.setAuthorId(author.getId());
            newComment.setAuthorName(author.getName());
            newComment.setText(newCommentDto.getText());
            newComment.setCreated(LocalDateTime.now());
            return mapper.toFullCommentDto(commentRepository.save(newComment));
        });
    }

    @Override
    @Transactional
    public FullCommentDto updateCommentByUser(Long authorId, Long commentId, CommentDto updatedCommentDto)
            throws NotFoundException, DataIntegrityViolation {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий не найден"));
        if (!comment.getAuthorId().equals(authorId)) {
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

    private Optional<UserShortDto> findUserById(Long userId) {
        //todo
        return Optional.empty();
    }
}