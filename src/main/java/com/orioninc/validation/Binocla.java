package com.orioninc.validation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.*;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Binocla(
        @NotBlank(message = "Имя не может отсутствовать") @Size(min = 2, message = "Имя не может быть таким коротким...") String firstName,
        @NotBlank(message = "Фамилия не может отсутствовать") @Size(min = 2, message = "Фамилия не может быть такой короткой...") String lastName,
        @NotBlank(message = "Карьерный рост не может быть пустым") String career,
        @Min(value = 1, message = "Возраст должен быть положительным") @Max(value = 100, message = "Возраст не может превышать 100 лет") @NotNull(message = "Возраст не может отсутствовать") Integer age,
        @URL @NotBlank String url) implements Serializable {
}
