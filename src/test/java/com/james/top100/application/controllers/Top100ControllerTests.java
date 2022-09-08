package com.james.top100.application.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.james.top100.application.TestProperties;
import com.james.top100.infrastructure.TmdbApi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class Top100ControllerTests {
  @Autowired TestProperties testProperties;
  @Autowired Top100Controller top100Controller;
  @Autowired MockMvc mockMvc;
  @MockBean TmdbApi tmdbApi;
  String baseUrl;

  @BeforeAll
  void beforeAll() {
    baseUrl = testProperties.getTop100BaseUrl();
  }

  @Test
  void loadsController() {
    assertNotNull(top100Controller);
  }

  @Test
  void getsHelloWorld() throws Exception {
    String url = baseUrl + "/";

    this.mockMvc.perform(get(url)).andExpect(content().string("Hello world"));
  }

  @Test
  void getsMovie() throws Exception {
    String movieId = "123";
    String url = baseUrl + "/movie/" + movieId;
    String movieData = "test";

    when(tmdbApi.getMovie(movieId)).thenReturn(movieData);

    this.mockMvc.perform(get(url)).andExpect(content().string(movieData));
  }
}
