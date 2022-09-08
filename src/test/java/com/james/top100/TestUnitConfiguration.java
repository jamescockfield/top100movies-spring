package com.james.top100;

import com.james.top100.application.TestProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("testUnit")
@Configuration
class TestUnitConfiguration {
  @Bean
  @Primary
  TestProperties testPropertiesBean() {
    TestProperties testProperties = new TestProperties();

    return testProperties;
  }
}
