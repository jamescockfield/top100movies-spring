package com.james.top100.application.configuration;

import com.james.top100.application.ApplicationProperties;
import com.james.top100.domain.services.DateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class ApplicationConfiguration {

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

  @Bean
  public Logger loggerBean() {
    // TODO: passing this class probably isn't proper practice - find the real way

    Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

    return logger;
  }

  @Bean
  public DateService dateServiceBean() {
    DateService dateService = new DateService();

    return dateService;
  }
}
