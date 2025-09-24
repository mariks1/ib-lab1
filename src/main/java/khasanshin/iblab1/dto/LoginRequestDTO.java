package khasanshin.iblab1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class LoginRequestDTO {
    @NotBlank
    String username;
    @NotBlank
    String password;
}

