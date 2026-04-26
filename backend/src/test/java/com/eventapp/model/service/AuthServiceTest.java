package com.eventapp.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.eventapp.model.entities.User;
import com.eventapp.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerShouldSaveUserWhenUsernameNotExists() {
        String username = "testuser";
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";


        given(userRepository.findByUsername(username)).willReturn(Optional.empty());
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());
        given(passwordEncoder.encode(password)).willReturn(encodedPassword);
        
        given(userRepository.save(any(User.class))).willAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        User result = authService.register(username, email, password);

        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        assertEquals(encodedPassword, result.getPassword());
        assertEquals("USER", result.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerShouldThrowExceptionWhenUsernameExists() {
        String username = "testuser";

        given(userRepository.findByUsername(username)).willReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            authService.register(username, "email", "password"));

        assertEquals("Username déjà utilisé", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void loginShouldReturnUserWhenCredentialsValid() {
        String username = "testuser";
        String password = "password";
        String encodedPassword = "encodedPassword";

        User user = new User(username, "email", encodedPassword, "USER");

        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(password, encodedPassword)).willReturn(true);

        User result = authService.login(username, password);

        assertEquals(user, result);
    }

    @Test
    void loginShouldThrowExceptionWhenUserNotFound() {
        String username = "testuser";

        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            authService.login(username, "password"));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void loginShouldThrowExceptionWhenPasswordInvalid() {
        String username = "testuser";
        String wrongPassword = "wrongpassword";
        String correctPassword = "encodedPassword";

        User user = new User(username, "email", correctPassword, "USER");

        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(wrongPassword, correctPassword)).willReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            authService.login(username, "wrongpassword"));

        assertEquals("Invalid password", exception.getMessage());
    }
}
