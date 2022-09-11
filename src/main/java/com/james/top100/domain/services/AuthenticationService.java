package com.james.top100.domain.services;

import java.util.ArrayList;

import com.james.top100.application.requests.LoginRequest;
import com.james.top100.application.requests.RegisterRequest;
import com.james.top100.application.security.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  @Autowired AuthenticationManager authenticationManager;
  @Autowired JwtUtils jwtUtils;
  @Autowired PasswordEncoder passwordEncoder;
  @Autowired UserDetailsManager userDetailsManager;
  @Autowired SecurityFilterChain securityFilterChain;

  public ResponseCookie loginAndGetSessionCookie(LoginRequest loginRequest) {
    login(loginRequest);
    ResponseCookie jwtCookie = generateJwtCookie(loginRequest);

    return jwtCookie;
  }

  private void login(LoginRequest loginRequest) {
    String username = loginRequest.getUsername();
    String password = loginRequest.getPassword();

    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(username, password);
    Authentication authentication = authenticationManager.authenticate(authToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private ResponseCookie generateJwtCookie(LoginRequest loginRequest) {
    String username = loginRequest.getUsername();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(username);

    return jwtCookie;
  }

  public boolean registerUser(RegisterRequest registerRequest) {
    String username = registerRequest.getUsername();

    // TODO: need to handle null case here
    if (userDetailsManager.userExists(username)) {
      return false;
    }

    String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

    // TODO: setup user roles
    User user = new User(username, encodedPassword, new ArrayList<GrantedAuthority>());

    userDetailsManager.createUser(user);

    return true;
  }
}
