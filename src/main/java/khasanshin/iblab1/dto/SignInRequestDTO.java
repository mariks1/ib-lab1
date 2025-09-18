package khasanshin.iblab1.dto;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDTO(
        @NotBlank String username,
        @NotBlank String password
) {}

