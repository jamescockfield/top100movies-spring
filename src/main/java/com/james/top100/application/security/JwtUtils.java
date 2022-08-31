package com.james.top100.application.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class JwtUtils {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
  private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

  @Autowired private Environment env;

  @Value("${jwtCookieName}")
  private String jwtCookieName;

  @Value("${jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${jwtSigningKey}")
  private String jwtSigningKeyString;

  private SecretKey jwtSigningKey;
  private JwtParser jwtParser;

  @PostConstruct
  private void postConstruct() {
    jwtSigningKey = getSigningKeyFromString(jwtSigningKeyString);

    jwtParser = Jwts.parserBuilder().setSigningKey(jwtSigningKey).build();
  }

  private SecretKey getSigningKeyFromString(String encodedKey) {
    System.out.println(String.format("env: %s", env));
    System.out.println(String.format("ENCODED KEY: %s", encodedKey));
    byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
    SecretKey signingKey = new SecretKeySpec(decodedKey, signatureAlgorithm.toString());

    return signingKey;
  }

  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
    if (cookie == null) {
      return null;
    }

    return cookie.getValue();
  }

  public String getUsernameFromJwtToken(String jwtToken) {
    String username = jwtParser.parseClaimsJws(jwtToken).getBody().getSubject();

    return username;
  }

  public Boolean validateJwtToken(String jwtToken) {
    try {
      jwtParser.parseClaimsJws(jwtToken);

      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("Expired JWT token: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("Unsupported JWT token: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  public ResponseCookie generateJwtCookie(String username) {
    String jwt = generateTokenFromUsername(username);

    ResponseCookie cookie = ResponseCookie.from(jwtCookieName, jwt).build();

    return cookie;
  }

  private String generateTokenFromUsername(String username) {
    Date issuedAt = new Date();
    Date expiration = new Date(new Date().getTime() + jwtExpirationMs);

    String token =
        Jwts.builder()
            .setSubject(username)
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(jwtSigningKey, signatureAlgorithm)
            .compact();

    return token;
  }
}
