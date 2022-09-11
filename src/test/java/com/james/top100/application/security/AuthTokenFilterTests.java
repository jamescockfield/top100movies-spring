package com.james.top100.application.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.james.top100.application.TestProperties;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@WithMockUser(username = TestProperties.MOCK_USERNAME)
class AuthTokenFilterTests {
  @MockBean JwtUtils jwtUtils;
  @MockBean SecurityContext securityContext;
  @MockBean HttpServletRequest request;
  @MockBean HttpServletResponse response;
  @MockBean FilterChain filterChain;

  @Captor ArgumentCaptor<Authentication> authenticationCaptor;

  AuthTokenFilter authTokenFilter = new AuthTokenFilter();

  @BeforeAll
  void beforeAll() {
    SecurityContextHolder.setContext(securityContext);
  }

  @BeforeEach
  void beforeEach() {
    clearInvocations(securityContext);
  }

  @Test
  void filtersValidJwt() {
    String validJwt = "validJwt";

    when(jwtUtils.getJwtFromCookies(request)).thenReturn(validJwt);
    when(jwtUtils.validateJwtToken(validJwt)).thenReturn(true);
    when(jwtUtils.getUsernameFromJwtToken(validJwt)).thenReturn(TestProperties.MOCK_USERNAME);

    // TODO: need to configure spring security test properly for this case to pass

    authTokenFilter.doFilterInternal(request, response, filterChain);

    verify(securityContext).setAuthentication(authenticationCaptor.capture());

    assertEquals("test", authenticationCaptor.getValue());
  }

  @Test
  void filtersNoJwt() {
    when(jwtUtils.getJwtFromCookies(request)).thenReturn(null);

    authTokenFilter.doFilterInternal(request, response, filterChain);

    verify(securityContext, never()).setAuthentication(any());
  }

  @Test
  void filtersInvalidJwt() {
    String invalidJwt = "invalidJwt";

    when(jwtUtils.getJwtFromCookies(request)).thenReturn(invalidJwt);
    when(jwtUtils.validateJwtToken(invalidJwt)).thenReturn(false);

    authTokenFilter.doFilterInternal(request, response, filterChain);

    verify(securityContext, never()).setAuthentication(any());
  }
}
