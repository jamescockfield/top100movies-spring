package com.james.top100.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TMDBApi {

  RestTemplate restTemplate;

  public TMDBApi() {
    restTemplate = new RestTemplate();
  }

  // TODO: create a bean with these properties strictly mapped
  @Value("${tmdb.access.token}")
  private String accessToken;

  @Value("${tmdb.api.url}")
  private String apiBaseUrl;

  private HttpHeaders getAuthHeader() {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    return headers;
  }

  public String getMovie(String movieId) {
    String url = String.format("%s/movie/%s", apiBaseUrl, movieId);

    HttpHeaders headers = getAuthHeader();
    HttpEntity<String> entity = new HttpEntity<>(headers);

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class);

    return movieId;
  }
}
