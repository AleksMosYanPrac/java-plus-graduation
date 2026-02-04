package ru.practicum.ewm.core.api.contracts.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewUserRequest {

    @Email
    @NotBlank
    @Length(min = 6, max = 254)
    private String email;

    @NotBlank
    @Length(min = 2, max = 250)
    private String name;
}