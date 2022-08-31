package com.james.top100.application.controllers;

import com.james.top100.application.requests.LoginRequest;
import com.james.top100.application.requests.RegisterRequest;
import com.james.top100.domain.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {
  @Autowired AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

    ResponseCookie jwtCookie = authenticationService.loginAndGetSessionCookie(loginRequest);

    if (jwtCookie == null) {
      // TODO: return specific login fail messages
      ResponseEntity<String> response =
          ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Login failed");

      return response;
    }

    ResponseEntity<String> response =
        ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body("Login successful");

    return response;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {

    boolean registerSuccessful = authenticationService.registerUser(registerRequest);

    // TODO: return specific register fail messages
    if (!registerSuccessful) {
      ResponseEntity<String> response = ResponseEntity.badRequest().body("Username already taken");

      return response;
    }

    ResponseEntity<String> response = ResponseEntity.ok().body("User saved successfully");

    return response;
  }
}
