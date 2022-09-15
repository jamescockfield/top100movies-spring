package com.james.top100.infrastructure.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

public class JwtBuilderFactory {
  public JwtBuilder getBuilder() {
    return Jwts.builder();
  }
}
