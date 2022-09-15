package com.james.top100.application;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Getter
@ToString
public class TestProperties extends ApplicationProperties {
  public static final String MOCK_USERNAME = "user";

  @Value("${top100.base.url}")
  private String top100BaseUrl;
}
