package com.james.top100.application.requests;

import lombok.Data;

@Data
public class RegisterRequest {
  private String username;
  private String password;
}
