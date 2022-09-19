package com.james.top100.application.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.james.top100.application.TestProperties;
import com.james.top100.infrastructure.TmdbApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser
@SpringBootTest
class MovieControllerTests {
  @Autowired TestProperties testProperties;
  @Autowired MovieController movieController;
  @MockBean TmdbApi tmdbApi;

  @Test
  void loadsController() {
    assertNotNull(movieController);
  }

  @Test
  void getsMovie() {
    String movieId = "123";
    String exptecteMovieData = "test";

    when(tmdbApi.getMovie(movieId)).thenReturn(exptecteMovieData);

    String movieData = movieController.getMovie(movieId);

    assertEquals(exptecteMovieData, movieData);
  }
}
