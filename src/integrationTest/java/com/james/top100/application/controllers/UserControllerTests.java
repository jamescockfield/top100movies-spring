package com.james.top100.application.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.james.top100.application.TestProperties;
import com.james.top100.application.requests.LoginRequest;
import com.james.top100.application.requests.RegisterRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class UserControllerTests {

  @Autowired TestProperties testProperties;
  @Autowired UserController userController;
  @Autowired MockMvc mockMvc;

  LoginRequest loginRequest = new LoginRequest();
  RegisterRequest registerRequest = new RegisterRequest();

  String baseUrl;

  @BeforeAll
  void beforeAll() {
    baseUrl = testProperties.getTop100BaseUrl() + "/user";
  }

  @Test
  void loginSuccess() throws Exception {
    String url = baseUrl + "/login";

    this.mockMvc
        .perform(get(url))
        .andExpect(content().string("Login successful"))
        .andExpect(status().is(200))
        .andExpect(cookie().value(HttpHeaders.SET_COOKIE, "cookieName=cookieValue"));
  }

  @Test
  void loginFailed() throws Exception {
    String url = baseUrl + "/login";

    this.mockMvc
        .perform(get(url))
        .andExpect(content().string("Login failed"))
        .andExpect(status().is(401));
  }

  @Test
  void registerSuccess() throws Exception {
    String url = baseUrl + "/register";

    this.mockMvc
        .perform(get(url))
        .andExpect(content().string("User saved successfully"))
        .andExpect(status().is(200));
  }

  @Test
  void registerFailed() throws Exception {
    String url = baseUrl + "/register";

    this.mockMvc
        .perform(get(url))
        .andExpect(content().string("Username already taken"))
        .andExpect(status().is(400));
  }
}
