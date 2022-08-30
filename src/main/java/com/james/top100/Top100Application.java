package com.james.top100;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Top100Application {

  public static void main(String[] args) {
    SpringApplication.run(Top100Application.class, args);
  }
}
