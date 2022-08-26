package com.james.top100;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("testUnit")
@Configuration
class TestUnitConfiguration {}
