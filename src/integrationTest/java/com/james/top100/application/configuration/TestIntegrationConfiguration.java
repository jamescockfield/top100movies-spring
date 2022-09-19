package com.james.top100.application.configuration;

import com.james.top100.application.TestProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("testIntegration")
@Configuration
class TestIntegrationConfiguration {
  @Bean
  @Primary
  TestProperties testPropertiesBean() {
    TestProperties testProperties = new TestProperties();

    return testProperties;
  }
}
