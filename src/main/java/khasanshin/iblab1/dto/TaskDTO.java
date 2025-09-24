package khasanshin.iblab1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class TaskDTO {

    Long id;

    @NotBlank String title;

    String description;
}