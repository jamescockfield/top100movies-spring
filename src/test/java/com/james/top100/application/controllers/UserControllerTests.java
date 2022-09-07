package com.james.top100.application.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.james.top100.application.requests.LoginRequest;
import com.james.top100.application.requests.RegisterRequest;
import com.james.top100.domain.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class UserControllerTests {

  @MockBean AuthenticationService authenticationService;
  @Autowired UserController userController;

  LoginRequest loginRequest = new LoginRequest();
  RegisterRequest registerRequest = new RegisterRequest();

  @Test
  void loginSuccess() {
    ResponseCookie mockJwtCookie = ResponseCookie.from("cookieName", "cookieValue").build();

    when(authenticationService.loginAndGetSessionCookie(loginRequest)).thenReturn(mockJwtCookie);

    ResponseEntity<String> responseEntity = userController.login(loginRequest);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Login successful", responseEntity.getBody());
    assertEquals(
        "cookieName=cookieValue", responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
  }

  @Test
  void loginFailed() {
    when(authenticationService.loginAndGetSessionCookie(loginRequest)).thenReturn(null);

    ResponseEntity<String> responseEntity = userController.login(loginRequest);

    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    assertEquals("Login failed", responseEntity.getBody());
  }

  @Test
  void registerSuccess() {
    when(authenticationService.registerUser(registerRequest)).thenReturn(true);

    ResponseEntity<String> responseEntity = userController.register(registerRequest);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("User saved successfully", responseEntity.getBody());
  }

  @Test
  void registerFailed() {
    when(authenticationService.registerUser(registerRequest)).thenReturn(false);

    ResponseEntity<String> responseEntity = userController.register(registerRequest);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals("Username already taken", responseEntity.getBody());
  }
}
