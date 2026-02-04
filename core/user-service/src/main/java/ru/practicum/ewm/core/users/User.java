package ru.practicum.ewm.core.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Length(min = 6, max = 254)
    private String email;

    @NotBlank
    @Length(min = 2, max = 250)
    private String name;
}