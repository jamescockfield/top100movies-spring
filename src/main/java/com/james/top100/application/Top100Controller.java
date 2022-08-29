package com.james.top100.application;

import com.james.top100.infrastructure.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Top100Controller {

  @Autowired private TmdbApi tmdbApi;

  @GetMapping("/")
  public String root() {
    return "Hello world";
  }

  @GetMapping("/movie/{movieId}")
  public String getMovie(@PathVariable String movieId) {
    String response = tmdbApi.getMovie(movieId);

    return response;
  }
}
