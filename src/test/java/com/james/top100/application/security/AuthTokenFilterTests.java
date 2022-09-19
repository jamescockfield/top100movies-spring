package com.james.top100.application.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.james.top100.application.TestProperties;
import com.james.top100.infrastructure.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@WithMockUser(username = TestProperties.MOCK_USERNAME)
class AuthTokenFilterTests {

  @MockBean JwtUtils mockJwtUtils;
  // @MockBean UserDetailsService mockUserDetailsService;
  @Autowired UserDetailsManager mockUserDetailsService;
  @MockBean SecurityContextWrapper mockSecurityContextWrapper;

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  SecurityContext mockSecurityContext;

  @Mock HttpServletRequest mockRequest;
  @Mock HttpServletResponse mockResponse;
  @Mock FilterChain mockFilterChain;
  @Mock UserDetails mockUserDetails;

  @Captor ArgumentCaptor<Authentication> authenticationCaptor;

  @Autowired AuthTokenFilter authTokenFilter;

  @BeforeAll
  void beforeAll() {
    when(mockSecurityContextWrapper.getContext()).thenReturn(mockSecurityContext);
    // SecurityContextHolder.setContext(mockSecurityContext);
  }

  @BeforeEach
  void beforeEach() {
    clearInvocations(mockSecurityContext);
  }

  @Test
  void filtersValidJwt() {
    String validJwt = "validJwt";

    when(mockUserDetailsService.loadUserByUsername(TestProperties.MOCK_USERNAME))
        .thenReturn(mockUserDetails);
    when(mockJwtUtils.getJwtFromCookies(mockRequest)).thenReturn(validJwt);
    when(mockJwtUtils.validateJwtToken(validJwt)).thenReturn(true);
    when(mockJwtUtils.getUsernameFromJwtToken(validJwt)).thenReturn(TestProperties.MOCK_USERNAME);

    authTokenFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

    verify(mockSecurityContext).setAuthentication(authenticationCaptor.capture());

    assertEquals("test", authenticationCaptor.getValue());
  }

  @Test
  void filtersNoJwt() {
    when(mockJwtUtils.getJwtFromCookies(mockRequest)).thenReturn(null);

    authTokenFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

    verify(mockSecurityContext, never()).setAuthentication(any());
  }

  @Test
  void filtersInvalidJwt() {
    String invalidJwt = "invalidJwt";

    when(mockJwtUtils.getJwtFromCookies(mockRequest)).thenReturn(invalidJwt);
    when(mockJwtUtils.validateJwtToken(invalidJwt)).thenReturn(false);

    authTokenFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

    verify(mockSecurityContext, never()).setAuthentication(any());
  }
}
