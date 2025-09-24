package khasanshin.iblab1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class SignInRequestDTO {
    @NotBlank
    String username;
    @NotBlank
    String password;
}

