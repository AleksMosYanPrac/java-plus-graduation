package ru.practicum.ewm.core.comments;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authorId;

    @NotBlank
    @Length(min = 2, max = 250)
    private String authorName;

    private Long eventId;

    private String text;

    private LocalDateTime created;
}