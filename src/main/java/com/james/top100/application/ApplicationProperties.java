package com.james.top100.application;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Getter
@ToString
public class ApplicationProperties {

  @Value("${tmdb.access.token}")
  private String accessToken;

  @Value("${tmdb.api.url}")
  private String apiBaseUrl;

  // TOOD: maybe extract these to their own JwtProperties?
  @Value("${jwtCookieName}")
  private String jwtCookieName;

  @Value("${jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${jwtSigningKey}")
  private String jwtSigningKeyString;
}
