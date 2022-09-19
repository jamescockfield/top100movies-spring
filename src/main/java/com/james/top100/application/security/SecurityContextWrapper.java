package com.james.top100.application.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextWrapper {
  public SecurityContext getContext() {
    return SecurityContextHolder.getContext();
  }
}
