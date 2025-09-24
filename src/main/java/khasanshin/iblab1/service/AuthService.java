package khasanshin.iblab1.service;

import jakarta.persistence.EntityNotFoundException;
import khasanshin.iblab1.dto.JwtAuthenticationResponseDTO;
import khasanshin.iblab1.dto.LoginRequestDTO;
import khasanshin.iblab1.dto.RegisterRequestDTO;
import khasanshin.iblab1.entity.User;
import khasanshin.iblab1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    public JwtAuthenticationResponseDTO register(RegisterRequestDTO request) {

        userRepository.findByUsername(request.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        });

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userService.create(user);

        var userDetails = userService.userDetailsService().loadUserByUsername(user.getUsername());
        String jwt = jwtService.generateToken(userDetails);

        return new JwtAuthenticationResponseDTO(jwt);
    }

    public JwtAuthenticationResponseDTO login(LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(ex.getMessage());
        }

        userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        var userDetails = userService.userDetailsService().loadUserByUsername(request.getUsername());
        String jwt = jwtService.generateToken(userDetails);

        return new JwtAuthenticationResponseDTO(jwt);
    }

}