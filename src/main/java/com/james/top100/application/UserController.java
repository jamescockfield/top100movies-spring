package com.james.top100.application;

import com.james.top100.application.requests.LoginRequest;
import com.james.top100.application.requests.RegisterRequest;
import com.james.top100.application.security.JwtUtils;
import com.james.top100.domain.entities.User;
import com.james.top100.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {
  @Autowired AuthenticationManager authenticationManager;
  @Autowired JwtUtils jwtUtils;
  @Autowired UserRepository userRepository;
  @Autowired PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
    String username = loginRequest.getUsername();
    String password = loginRequest.getPassword();
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(username, password);
    Authentication authentication = authenticationManager.authenticate(authToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
    ResponseEntity<String> response =
        ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body("Login successful");

    return response;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
    String username = registerRequest.getUsername();
    if (userRepository.existsByUsername(username)) {
      return ResponseEntity.badRequest().body("Username already taken");
    }
    String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
    User user = new User(username, encodedPassword);

    // TODO: setup user roles

    userRepository.save(user);
    ResponseEntity<String> response = ResponseEntity.ok().body("User saved successfully");

    return response;
  }
}
