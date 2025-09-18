package khasanshin.iblab1.service;

import jakarta.persistence.EntityNotFoundException;
import khasanshin.iblab1.dto.JwtAuthenticationResponseDTO;
import khasanshin.iblab1.dto.SignInRequestDTO;
import khasanshin.iblab1.dto.SignUpRequestDTO;
import khasanshin.iblab1.entity.UserEntity;
import khasanshin.iblab1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public JwtAuthenticationResponseDTO signUp(SignUpRequestDTO request) {

        userRepository.findByUsername(request.username()).ifPresent(u -> {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        });

        UserEntity user = UserEntity.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userService.create(user);

        var userDetails = userService.userDetailsService().loadUserByUsername(user.getUsername());
        String jwt = jwtService.generateToken(userDetails);

        return new JwtAuthenticationResponseDTO(jwt);
    }

    public JwtAuthenticationResponseDTO signIn(SignInRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        userRepository.findByUsername(request.username())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        var userDetails = userService.userDetailsService().loadUserByUsername(request.username());
        String jwt = jwtService.generateToken(userDetails);

        return new JwtAuthenticationResponseDTO(jwt);
    }

}