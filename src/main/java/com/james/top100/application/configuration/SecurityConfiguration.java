package com.james.top100.application.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
class SecurityConfiguration {

  @Bean
  public PasswordEncoder passwordEncoderBean() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean(
      AuthenticationConfiguration authenticationConfiguration,
      ObjectPostProcessor<Object> objectPostProcessor,
      ApplicationContext applicationContext)
      throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        authenticationConfiguration.authenticationManagerBuilder(
            objectPostProcessor, applicationContext);
    authenticationManagerBuilder.inMemoryAuthentication();

    AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

    return authenticationManager;
  }

  @Bean
  public InMemoryUserDetailsManager inMemoryUserDetailsManagerBean() {
    InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

    return inMemoryUserDetailsManager;
  }

  @Bean
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers("/", "/login", "/register")
        .permitAll()
        .anyRequest()
        .authenticated();

    SecurityFilterChain filterChain = http.build();

    return filterChain;
  }
}
