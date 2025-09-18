package khasanshin.iblab1.dto;

import jakarta.validation.constraints.NotBlank;

public record SignUpRequestDTO(
        @NotBlank String username,
        @NotBlank String password
) {}

