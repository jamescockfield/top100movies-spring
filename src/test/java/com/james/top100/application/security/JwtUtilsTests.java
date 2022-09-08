package com.james.top100.application.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootTest
class JwtUtilsTests {
  @Autowired JwtUtils jwtUtils;

  @MockBean Logger logger;

  String jwtToken = "jwtToken";

  @Test
  void getsJwtFromCookies() {
    HttpServletRequest request = new HttpServletRequest();

    String jwt = jwtUtils.getJwtFromCookies(request);
  }

  @Test
  void getsUsernameFromJwtToken() {
    String username = jwtUtils.getUsernameFromJwtToken(jwtToken);

    assertEquals("username", username);
  }

  @Test
  void validatesJwtToken() {
    Boolean validated = jwtUtils.validateJwtToken(jwtToken);

    assertEquals("true", validated);
  }

  @Test
  void generatesJwtCookie() {
    String username = "username";

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(username);
    
  }
}
