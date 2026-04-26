package com.eventapp.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.eventapp.model.entities.User;
import com.eventapp.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        filter = new JwtAuthenticationFilter(jwtService, userRepository);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternalShouldContinueWhenHeaderMissing() throws ServletException, IOException {
        given(request.getHeader("Authorization")).willReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternalShouldContinueWhenHeaderNotBearer() throws ServletException, IOException {
        given(request.getHeader("Authorization")).willReturn("Basic abc123");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternalShouldSetAuthenticationWhenTokenValid() throws ServletException, IOException {
        String token = "valid-token";
        User user = new User("user", "user@example.com", "password", "USER");
        user.setId(100L);

        given(request.getHeader("Authorization")).willReturn("Bearer " + token);
        given(jwtService.extractUsername(token)).willReturn("user");
        given(userRepository.findByUsername("user")).willReturn(Optional.of(user));
        given(jwtService.isTokenValid(token, user)).willReturn(true);

        filter.doFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(user, authentication.getPrincipal());
        assertEquals(1, authentication.getAuthorities().size());
        assertEquals("ROLE_USER", authentication.getAuthorities().iterator().next().getAuthority());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternalShouldContinueWhenUserNotFound() throws ServletException, IOException {
        String token = "missing-user-token";

        given(request.getHeader("Authorization")).willReturn("Bearer " + token);
        given(jwtService.extractUsername(token)).willReturn("unknown");
        given(userRepository.findByUsername("unknown")).willReturn(Optional.empty());

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
