package com.james.top100.infrastructure;

import com.james.top100.application.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TmdbApi {

  @Autowired RestTemplate restTemplate;
  @Autowired ApplicationProperties applicationProperties;

  private HttpHeaders getAuthHeader() {
    HttpHeaders headers = new HttpHeaders();
    String accessToken = applicationProperties.getAccessToken();
    headers.setBearerAuth(accessToken);

    return headers;
  }

  public String getMovie(String movieId) {
    String baseUrl = applicationProperties.getApiBaseUrl();
    String url = String.format("%s/movie/%s", baseUrl, movieId);

    HttpHeaders headers = getAuthHeader();
    HttpEntity<String> entity = new HttpEntity<>(headers);

    restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class);

    return movieId;
  }
}
