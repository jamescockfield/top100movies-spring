package com.james.top100.application.configuration;

import com.james.top100.application.ApplicationProperties;
import com.james.top100.application.security.AuthTokenFilter;
import com.james.top100.infrastructure.utils.JwtBuilderFactory;
import com.james.top100.infrastructure.utils.JwtUtils;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
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
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
class SecurityConfiguration {

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
  public UserDetailsManager userDetailsManagerBean() {
    InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

    return userDetailsManager;
  }

  @Bean
  public PasswordEncoder passwordEncoderBean() {
    return new BCryptPasswordEncoder();
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

  // TODO: since we are using spring default security instead of jwt, we can consider removing these

  @Bean
  SecretKey jwtSigningKeyBean(ApplicationProperties applicationProperties) {
    String jwtKeySigningString = applicationProperties.getJwtSigningKeyString();

    SecretKey jwtSigningKey = JwtUtils.getSigningKeyFromString(jwtKeySigningString);

    return jwtSigningKey;
  }

  @Bean
  JwtParser jwtParserBean(SecretKey jwtSigningKey) {
    JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(jwtSigningKey).build();

    return jwtParser;
  }

  @Bean
  JwtBuilderFactory jwtsBuilderFactoryBean() {
    return new JwtBuilderFactory();
  }

  @Bean
  AuthTokenFilter authTokenFilterBean() {
    AuthTokenFilter authTokenFilter = new AuthTokenFilter();

    return authTokenFilter;
  }
}
