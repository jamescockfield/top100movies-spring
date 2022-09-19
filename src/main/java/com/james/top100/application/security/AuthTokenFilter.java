package com.james.top100.application.security;

import com.james.top100.infrastructure.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

// TODO: currently unused, consider removing whole JWT implementation
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired JwtUtils jwtUtils;
  @Autowired UserDetailsService userDetailsService;
  @Autowired SecurityContextWrapper securityContextWrapper;

  // TODO: migrate from JWT to OAuth

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
    String jwt = parseJwt(request);

    if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
      String username = jwtUtils.getUsernameFromJwtToken(jwt);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      WebAuthenticationDetails authenticationDetails = new WebAuthenticationDetails(request);

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null);
      authentication.setDetails(authenticationDetails);

      securityContextWrapper.getContext().setAuthentication(authentication);
    }
  }

  private String parseJwt(HttpServletRequest request) {
    String jwt = jwtUtils.getJwtFromCookies(request);

    return jwt;
  }
}
