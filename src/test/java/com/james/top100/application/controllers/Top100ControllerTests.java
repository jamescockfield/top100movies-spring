package com.james.top100.application.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser
@SpringBootTest
class Top100ControllerTests {
  @Autowired TestRestTemplate restTemplate;

  @Test
  void getsHelloWorld() {
    String url = "http://localhost:8080/";

    String greeting = this.restTemplate.getForObject(url, String.class);

    assertEquals("Hello world", greeting);
  }

  @Test
  void getsMovie() {
    Integer movieId = 123;
    String url = String.format("http://localhost:8080/movie/%d", movieId);

    String movieData = this.restTemplate.getForObject(url, String.class);

    assertEquals("test", movieData);
  }
}
