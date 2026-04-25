package com.eventapp.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.eventapp.exception.AuthException.UserNotFoundException;
import com.eventapp.model.entities.User;
import com.eventapp.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getByUsernameShouldReturnUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        User result = userService.getByUsername("john");

        assertThat(result.getUsername()).isEqualTo("john");
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getByUsernameShouldThrowWhenUserNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getByUsername("unknown"))
                .isInstanceOf(UserNotFoundException.class);
    }
}