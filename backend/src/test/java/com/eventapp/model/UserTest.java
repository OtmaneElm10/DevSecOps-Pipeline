package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.User;

class UserTest {

    @Test
    void constructorShouldSetAllFields() {
        User user = new User("testuser", "test@example.com", "password", "USER");

        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getRole()).isEqualTo("USER");
    }

    @Test
    void defaultConstructorShouldCreateEmptyUser() {
        User user = new User();
        assertThat(user.getId()).isNull();
        assertThat(user.getUsername()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getPassword()).isNull();
        assertThat(user.getRole()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole("ADMIN");

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getRole()).isEqualTo("ADMIN");
    }
}
