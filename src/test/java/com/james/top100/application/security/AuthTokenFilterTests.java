package com.james.top100.application.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SpringBootTest
class AuthTokenFilterTests {
  @MockBean JwtUtils jwtUtils;
  @MockBean UserDetailsService userDetailsService;
  @MockBean SecurityContextHolder securityContextHolder;

  @Autowired AuthTokenFilter authTokenFilter;

  @Test
  void doesFilterInternal() {
    HttpServletRequest request = new HttpServletRequest();
    HttpServletResponse response = new HttpServletResponse();
    FilterChain filterChain = new FilterChain();

    authTokenFilter.doFilterInternal(request, response, filterChain);

    when(securityContextHolder.getContext())

    assertEquals(null, securityContextHolder.getContext().getAuthentication());

    throw new Exception("not implemented");
  }
}
