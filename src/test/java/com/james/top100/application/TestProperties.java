package com.james.top100.application;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TestProperties extends ApplicationProperties {
  @Value("${top100.base.url}")
  private String top100BaseUrl;
}
