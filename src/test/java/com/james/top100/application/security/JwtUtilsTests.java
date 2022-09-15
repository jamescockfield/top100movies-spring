package com.james.top100.application.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.james.top100.application.TestProperties;
import com.james.top100.domain.services.DateService;
import com.james.top100.infrastructure.utils.JwtBuilderFactory;
import com.james.top100.infrastructure.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseCookie;
import org.springframework.web.util.WebUtils;

@SpringBootTest
class JwtUtilsTests {
  @Autowired TestProperties testProperties;
  @Autowired JwtUtils jwtUtils;

  @MockBean HttpServletRequest mockRequest;
  @MockBean JwtParser mockJwtParser;
  @MockBean Logger mockLogger;
  @MockBean SecretKey mockJwtSigningKey;
  @MockBean JwtBuilderFactory mockJwtBuilderFactory;
  @MockBean DateService dateService;

  @Mock(answer = Answers.RETURNS_SELF)
  JwtBuilder mockJwtBuilder;

  @Mock Jws<Claims> mockClaim;
  @Mock Claims mockClaimBody;

  @Captor ArgumentCaptor<String> usernameCaptor;
  @Captor ArgumentCaptor<Date> issuedAtCaptor;
  @Captor ArgumentCaptor<Date> expirationCaptor;
  @Captor ArgumentCaptor<SecretKey> jwtSigningKeyCaptor;
  @Captor ArgumentCaptor<SignatureAlgorithm> signatureAlgorithmCaptor;
  @Captor ArgumentCaptor<String> errorLogCaptor;
  @Captor ArgumentCaptor<String> errorMessageCaptor;

  String jwtToken = "jwtToken";
  String mockUsername = "username";

  @Test
  void getsJwtFromCookies() {
    String jwtCookieName = testProperties.getJwtCookieName();

    Cookie cookie = new Cookie("name", "jwt");

    MockedStatic<WebUtils> webUtils = mockStatic(WebUtils.class);
    webUtils.when(() -> WebUtils.getCookie(mockRequest, jwtCookieName)).thenReturn(cookie);

    String jwt = jwtUtils.getJwtFromCookies(mockRequest);

    assertEquals("jwt", jwt);
  }

  @Test
  void getsUsernameFromJwtToken() {
    when(mockJwtParser.parseClaimsJws(jwtToken)).thenReturn(mockClaim);
    when(mockClaim.getBody()).thenReturn(mockClaimBody);
    when(mockClaimBody.getSubject()).thenReturn(mockUsername);

    String username = jwtUtils.getUsernameFromJwtToken(jwtToken);

    assertEquals(mockUsername, username);
  }

  @Test
  void validatesJwtToken() {
    Boolean validated = jwtUtils.validateJwtToken(jwtToken);

    assertEquals(true, validated);
  }

  @Test
  void invalidatesJwtToken() {
    when(mockJwtParser.parseClaimsJws(jwtToken)).thenThrow(new SignatureException("whoops"));

    Boolean validated = jwtUtils.validateJwtToken(jwtToken);

    verify(mockLogger).error(errorMessageCaptor.capture(), errorLogCaptor.capture());

    assertEquals(false, validated);
    assertEquals("Invalid JWT signature: {}", errorMessageCaptor.getValue());
    assertEquals("whoops", errorLogCaptor.getValue());
  }

  @Test
  void generatesJwtCookie() {
    Date currentDate = new Date();
    Date futureDate = new Date(new Date().getTime() + 500);
    String mockToken = "mockToken";

    when(dateService.getDateNow()).thenReturn(currentDate);
    when(dateService.getDateInFuture(any())).thenReturn(futureDate);
    when(mockJwtBuilderFactory.getBuilder()).thenReturn(mockJwtBuilder);
    when(mockJwtBuilder.compact()).thenReturn(mockToken);

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(mockUsername);

    verify(mockJwtBuilder).setSubject(usernameCaptor.capture());
    verify(mockJwtBuilder).setIssuedAt(issuedAtCaptor.capture());
    verify(mockJwtBuilder).setExpiration(expirationCaptor.capture());
    verify(mockJwtBuilder)
        .signWith(jwtSigningKeyCaptor.capture(), signatureAlgorithmCaptor.capture());
    verify(mockJwtBuilder).compact();

    assertEquals(mockUsername, usernameCaptor.getValue());
    assertEquals(currentDate, issuedAtCaptor.getValue());
    assertEquals(futureDate, expirationCaptor.getValue());
    assertEquals(mockJwtSigningKey, jwtSigningKeyCaptor.getValue());
    assertEquals(SignatureAlgorithm.HS512, signatureAlgorithmCaptor.getValue());
    assertEquals(testProperties.getJwtCookieName(), jwtCookie.getName());
    assertEquals(mockToken, jwtCookie.getValue());
  }
}
