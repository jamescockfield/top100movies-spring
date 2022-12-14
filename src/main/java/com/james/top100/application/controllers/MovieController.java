package com.james.top100.application.controllers;

import com.james.top100.infrastructure.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {
  @Autowired private TmdbApi tmdbApi;

  @GetMapping("/{movieId}")
  public String getMovie(@PathVariable String movieId) {
    String response = tmdbApi.getMovie(movieId);

    return response;
  }
}
