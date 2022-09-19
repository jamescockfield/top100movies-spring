package com.james.top100.application.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.james.top100.application.TestProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class HomeControllerTests {
  @Autowired TestProperties testProperties;
  @Autowired HomeController homeController;
  @Autowired MockMvc mockMvc;
  String baseUrl;

  @BeforeAll
  void beforeAll() {
    baseUrl = testProperties.getTop100BaseUrl();
  }

  @Test
  void loadsController() {
    assertNotNull(homeController);
  }

  @Test
  void getsHelloWorld() throws Exception {
    String url = baseUrl + "/";

    this.mockMvc.perform(get(url)).andExpect(content().string("Hello world"));
  }
}
