package khasanshin.iblab1.controller;

import jakarta.validation.Valid;
import khasanshin.iblab1.dto.JwtAuthenticationResponseDTO;
import khasanshin.iblab1.dto.MessageInfoDTO;
import khasanshin.iblab1.dto.SignInRequestDTO;
import khasanshin.iblab1.dto.SignUpRequestDTO;
import khasanshin.iblab1.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authenticationService;

    @PostMapping("/sign-in")
    public JwtAuthenticationResponseDTO signIn(@RequestBody @Valid SignInRequestDTO request) {
        return authenticationService.signIn(request);
    }

    @PostMapping("/sign-up")
    public JwtAuthenticationResponseDTO signUp(@RequestBody @Valid SignUpRequestDTO request) {
        return authenticationService.signUp(request);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageInfoDTO error(Exception ex) {
        return new MessageInfoDTO(ex.getMessage());
    }
}
