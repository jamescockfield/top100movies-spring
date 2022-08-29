package com.james.top100.infrastructure.repositories;

import com.james.top100.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Boolean existsByUsername(String username);
}
