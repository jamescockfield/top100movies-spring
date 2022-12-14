package com.james.top100.infrastructure.utils;

import com.james.top100.application.ApplicationProperties;
import com.james.top100.domain.services.DateService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

// TODO: since we are using spring default security instead of jwt, we can consider removing this

@Component
public class JwtUtils {

  private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

  @Autowired private ApplicationProperties applicationProperties;
  @Autowired private JwtParser jwtParser;
  @Autowired private Logger logger;
  @Autowired private SecretKey jwtSigningKey;
  @Autowired private JwtBuilderFactory jwtBuilderFactory;
  @Autowired private DateService dateService;

  private String jwtCookieName;

  @PostConstruct
  private void postConstruct() {
    jwtCookieName = applicationProperties.getJwtCookieName();
  }

  public static SecretKey getSigningKeyFromString(String encodedKey) {
    byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
    SecretKey signingKey = new SecretKeySpec(decodedKey, signatureAlgorithm.getJcaName());

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
    Integer jwtExpirationMs = applicationProperties.getJwtExpirationMs();

    Date issuedAt = dateService.getDateNow();
    Date expiration = dateService.getDateInFuture(jwtExpirationMs);

    JwtBuilder builder = jwtBuilderFactory.getBuilder();
    String token =
        builder
            .setSubject(username)
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(jwtSigningKey, signatureAlgorithm)
            .compact();

    return token;
  }
}
