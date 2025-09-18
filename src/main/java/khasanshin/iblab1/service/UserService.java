package khasanshin.iblab1.service;

import jakarta.persistence.EntityNotFoundException;
import khasanshin.iblab1.entity.UserEntity;
import khasanshin.iblab1.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new EntityNotFoundException("Пользователь с таким именем уже существует");
        }
        save(user);
    }


    @Transactional(rollbackFor = Exception.class)
    public void save(UserEntity user) {
        userRepository.save(user);
    }

    public UserDetailsService userDetailsService() {
        return username -> {
            UserEntity u = getByUsername(username);
            return new org.springframework.security.core.userdetails.User(
                    u.getUsername(),
                    u.getPassword(),
                    Collections.emptyList()
            );
        };
    }

}