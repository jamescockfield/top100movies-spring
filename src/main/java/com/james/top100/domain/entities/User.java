package com.james.top100.domain.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class User {
  private final String username;
  private final String password;
}
