package com.james.top100.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
  @Id private Long id;
  private final String username;
  private final String password;
}
