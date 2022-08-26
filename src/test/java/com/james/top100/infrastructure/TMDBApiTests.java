package com.james.top100.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Collections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("testUnit")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest()
class TMDBApiTestsLambda {

  @MockBean RestTemplate restTemplate;
  @Autowired TMDBApi tmdbApi;
  String movieId = "123";
  String expectedUrl = "apiUrl/movie/123";

  @Captor ArgumentCaptor<String> urlCaptor;
  @Captor ArgumentCaptor<HttpMethod> methodCaptor;
  @Captor ArgumentCaptor<HttpEntity<String>> requestCaptor;
  @Captor ArgumentCaptor<Class<String>> responseTypeCaptor;

  @BeforeAll
  void beforeAll() throws JSONException {
    JSONObject movieData = new JSONObject("{\"movie\": \"data\"}");
    MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();

    mockServer
        .expect(requestTo(expectedUrl))
        .andRespond(withSuccess(movieData.toString(), MediaType.APPLICATION_JSON));
  }

  @BeforeEach
  void beforeEach() {
    Mockito.clearInvocations(restTemplate);
  }

  @Test
  void getsMovie() {
    tmdbApi.getMovie(movieId);

    verify(restTemplate)
        .exchange(urlCaptor.capture(), methodCaptor.capture(), any(), responseTypeCaptor.capture());

    assertEquals("apiUrl/movie/123", urlCaptor.getValue());
    assertEquals(HttpMethod.GET, methodCaptor.getValue());
    assertEquals(JSONObject.class, responseTypeCaptor.getValue());
  }

  @Test
  void setsAuthHeader() {
    tmdbApi.getMovie(movieId);

    verify(restTemplate)
        .exchange(
            anyString(),
            any(HttpMethod.class),
            requestCaptor.capture(),
            ArgumentMatchers.<Class<JSONObject>>any());

    assertEquals(
        Collections.singletonList("Bearer accessToken"),
        requestCaptor.getValue().getHeaders().get("Authorization"));
  }
}
