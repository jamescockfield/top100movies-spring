package com.james.top100.application.configuration;

import com.james.top100.application.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestConfiguration {

  @Bean
  public RestTemplate restTemplateBean() {
    RestTemplate restTemplate = new RestTemplate();

    return restTemplate;
  }

  @Bean
  public ApplicationProperties applicationPropertiesBean() {
    ApplicationProperties properties = new ApplicationProperties();

    return properties;
  }
}
