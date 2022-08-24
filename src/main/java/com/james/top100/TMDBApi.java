package com.james.top100;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.client.RestTemplate;

// import org.springframework.web.client.RestTemplate;

public class TMDBApi {

  private String getAuthHeader() {
      return "";
        
  }
	public String getMovie(String movieId) {
    String url = "";

    JSONObject headers = new JSONObject();
    headers.put("header", "test");

    // need to set headers properly
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.postForObject(url, headers, JSONObject.class);

    return movieId;
	}
}
