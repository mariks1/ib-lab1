package khasanshin.iblab1.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskDTO(Long id, @NotBlank String title, String description) {}