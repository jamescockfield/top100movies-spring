package com.james.top100;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("testUnit")
@Configuration
class TestUnitConfiguration {

  @Bean
  public TestRestTemplate testRestTemplateBean() {
    TestRestTemplate testRestTemplate = new TestRestTemplate();

    return testRestTemplate;
  }
}
