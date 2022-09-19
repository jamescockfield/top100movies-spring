package com.james.top100.application.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.top100.application.TestProperties;
import com.james.top100.domain.types.RatingSetData;
import com.james.top100.infrastructure.entities.RatingSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class RatingControllerTests {

  @Autowired RatingController ratingController;
  @Autowired TestProperties testProperties;
  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;

  String username = "username";
  String baseUrl;

  @BeforeAll
  void beforeAll() {
    baseUrl = testProperties.getTop100BaseUrl() + "/ratings";
  }

  @Test
  void getsRatings() throws Exception {
    RatingSetData ratingSetData = new RatingSetData();
    ratingSetData.put(1, 1);
    RatingSet ratingSet = new RatingSet(username, ratingSetData);

    String ratingSetJson = objectMapper.writeValueAsString(ratingSet);

    String url = baseUrl + "/";

    this.mockMvc
        .perform(get(url))
        .andExpect(content().json(ratingSetJson))
        .andExpect(status().is(200));
  }

  @Test
  void updatesRatings() throws Exception {
    RatingSetData updatedRatingSetData = new RatingSetData();
    updatedRatingSetData.put(2, 2);
    RatingSet updatedRatingSet = new RatingSet(username, updatedRatingSetData);

    String updatedRatingSetJson = objectMapper.writeValueAsString(updatedRatingSet);

    String url = baseUrl + "/update";

    this.mockMvc
        .perform(post(url))
        .andExpect(content().json(updatedRatingSetJson))
        .andExpect(status().is(200));
  }

  @Test
  void resetsRatings() throws Exception {
    RatingSetData emptyRatingSetData = new RatingSetData();
    RatingSet emptyRatingSet = new RatingSet(username, emptyRatingSetData);

    String emptyRatingSetJson = objectMapper.writeValueAsString(emptyRatingSet);

    String url = baseUrl + "/reset";

    this.mockMvc
        .perform(post(url))
        .andExpect(content().json(emptyRatingSetJson))
        .andExpect(status().is(200));
  }
}
