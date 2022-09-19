package com.james.top100.application.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.james.top100.domain.services.RatingService;
import com.james.top100.domain.types.RatingSetData;
import com.james.top100.infrastructure.entities.RatingSet;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

@SpringBootTest
class RatingControllerTests {

  @Autowired RatingController ratingController;
  @MockBean RatingService ratingService;
  @Mock User user;

  String username = "username";

  @Test
  void getsRatings() {
    RatingSetData ratingSetData = new RatingSetData();
    ratingSetData.put(1, 1);
    RatingSet ratingSet = new RatingSet(username, ratingSetData);

    when(ratingService.getRatings(username)).thenReturn(ratingSet);

    ResponseEntity<RatingSet> responseEntity = ratingController.getRatings(user);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(ratingSet, responseEntity.getBody());
  }

  @Test
  void updatesRatings() {
    RatingSetData updatedRatingSetData = new RatingSetData();
    updatedRatingSetData.put(2, 2);
    RatingSet updatedRatingSet = new RatingSet(username, updatedRatingSetData);

    when(ratingService.updateRatings(username, any())).thenReturn(updatedRatingSet);

    ResponseEntity<RatingSet> responseEntity =
        ratingController.updateRatings(user, updatedRatingSetData);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(updatedRatingSet, responseEntity.getBody());
  }

  @Test
  void resetsRatings() {
    RatingSetData emptyRatingSetData = new RatingSetData();
    RatingSet emptyRatingSet = new RatingSet(username, emptyRatingSetData);

    when(ratingService.resetRatings(username)).thenReturn(emptyRatingSet);

    ResponseEntity<RatingSet> responseEntity = ratingController.resetRatings(user);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(emptyRatingSet, responseEntity.getBody());
  }
}
