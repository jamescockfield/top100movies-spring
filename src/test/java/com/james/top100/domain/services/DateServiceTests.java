package com.james.top100.domain.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DateServiceTests {

  @Autowired DateService dateService;

  @Test
  void getsDateNow() {}

  @Test
  void getsDateInPast() {}

  @Test
  void getsDateInFuture() {}
}
