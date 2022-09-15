package com.james.top100.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.james.top100.application.requests.LoginRequest;
import com.james.top100.application.requests.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;

@SpringBootTest
class AuthenticationServiceTests {

  @Autowired AuthenticationService authenticationService;

  @MockBean UserDetailsManager userRepository;

  String username = "username";
  String password = "password";

  @Test
  void logsInAndGetsSessionCookie() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername(username);
    loginRequest.setPassword(password);

    ResponseCookie sessionCookie = authenticationService.loginAndGetSessionCookie(loginRequest);

    assertEquals(true, sessionCookie);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    assertEquals(true, authentication);
  }

  @Test
  void registerInvalidatesAlreadyExistingUser() {
    when(userRepository.userExists(username)).thenReturn(true);

    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setUsername(username);
    registerRequest.setPassword(password);

    Boolean userWasRegistered = authenticationService.registerUser(registerRequest);

    assertEquals(false, userWasRegistered);
  }

  @Test
  void registersUser() {
    when(userRepository.userExists(username)).thenReturn(false);

    RegisterRequest registerRequest = new RegisterRequest();

    authenticationService.registerUser(registerRequest);
  }
}
