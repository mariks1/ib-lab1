package khasanshin.iblab1.controller;

import jakarta.validation.Valid;
import khasanshin.iblab1.dto.JwtAuthenticationResponseDTO;
import khasanshin.iblab1.dto.MessageInfoDTO;
import khasanshin.iblab1.dto.LoginRequestDTO;
import khasanshin.iblab1.dto.RegisterRequestDTO;
import khasanshin.iblab1.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authenticationService;

    @PostMapping("/login")
    public JwtAuthenticationResponseDTO login(@RequestBody @Valid LoginRequestDTO request) {
        return authenticationService.login(request);
    }

    @PostMapping("/register")
    public JwtAuthenticationResponseDTO register(@RequestBody @Valid RegisterRequestDTO request) {
        return authenticationService.register(request);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageInfoDTO error(Exception ex) {
        return new MessageInfoDTO(ex.getMessage());
    }
}
