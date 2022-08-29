package com.james.top100.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// TODO: remove deprecation
@EnableWebSecurity
@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers("/", "/login", "/register")
        .permitAll()
        .anyRequest()
        .authenticated();
  }
}
