package com.james.top100.application.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

import com.james.top100.application.TestProperties;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseCookie;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@SpringBootTest
class JwtUtilsTests {
  @Autowired JwtUtils jwtUtils;
  @Autowired
  TestProperties testProperties;

  @MockBean HttpServletRequest request;
  @MockBean Logger logger;

  String jwtToken = "jwtToken";

  @Test
  void getsJwtFromCookies() {
    String jwtCookieName = testProperties.getJwtCookieName();

    Cookie cookie = new Cookie("name", "jwt");

    MockedStatic<WebUtils> webUtils = mockStatic(WebUtils.class);
    webUtils.when(() -> WebUtils.getCookie(request, jwtCookieName)).thenReturn(cookie);

    String jwt = jwtUtils.getJwtFromCookies(request);

    assertEquals("jwt", jwt);
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

    assertEquals("jwtCookie", jwtCookie);
  }
}
