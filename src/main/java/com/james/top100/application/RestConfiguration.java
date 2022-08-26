package com.james.top100.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    return restTemplate;
  }

  @Bean
  public ApplicationProperties applicationProperties() {
    ApplicationProperties properties = new ApplicationProperties();

    return properties;
  }
}
