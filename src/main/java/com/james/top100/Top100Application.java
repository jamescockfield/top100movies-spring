package com.james.top100;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Top100Application {

	// TODO: SRP violation - seperate REST out from here?
	public static void main(String[] args) {
		SpringApplication.run(Top100Application.class, args);
	}

	@GetMapping("/")
	public String root() {
		return "Hello world";
	}

	@GetMapping("/movie/{movieId}")
	public String getMovie(@PathVariable String movieId) {
    TMDBApi tmdbApi = new TMDBApi();
		String response = tmdbApi.getMovie(movieId);

		return response;
	}
}
