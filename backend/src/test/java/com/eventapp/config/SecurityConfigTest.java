package com.eventapp.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import jakarta.servlet.Filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import com.eventapp.security.JwtAuthenticationFilter;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private HttpSecurity http;

    @Mock
    private DefaultSecurityFilterChain securityFilterChain;

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig(jwtAuthenticationFilter);
    }

    @Test
    void securityFilterChainShouldBuildSuccessfully() throws Exception {
        given(http.csrf(any())).willReturn(http);
        given(http.cors(any())).willReturn(http);
        given(http.sessionManagement(any())).willReturn(http);
        given(http.authorizeHttpRequests(any())).willReturn(http);
        given(http.addFilterBefore(any(Filter.class), org.mockito.ArgumentMatchers.<Class<? extends Filter>>any())).willReturn(http);
        given(http.build()).willReturn(securityFilterChain);

        SecurityFilterChain result = securityConfig.securityFilterChain(http);

        assertThat(result).isSameAs(securityFilterChain);
    }

    @Test
    void passwordEncoderShouldReturnBCryptEncoder() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();

        assertThat(encoder).isInstanceOf(BCryptPasswordEncoder.class);
    }
}
