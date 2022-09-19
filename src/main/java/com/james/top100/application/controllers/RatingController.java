package com.james.top100.application.controllers;

import com.james.top100.domain.services.RatingService;
import com.james.top100.domain.types.RatingSetData;
import com.james.top100.infrastructure.entities.RatingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
class RatingController {
  @Autowired RatingService ratingService;

  @GetMapping("/")
  public ResponseEntity<RatingSet> getRatings(@AuthenticationPrincipal User user) {
    String username = user.getUsername();

    RatingSet ratings = ratingService.getRatings(username);

    ResponseEntity<RatingSet> response = ResponseEntity.ok().body(ratings);

    return response;
  }

  @PostMapping("/update")
  public ResponseEntity<RatingSet> updateRatings(
      @AuthenticationPrincipal User user, @RequestBody RatingSetData ratingSetData) {
    String username = user.getUsername();

    RatingSet ratings = ratingService.updateRatings(username, ratingSetData);

    ResponseEntity<RatingSet> response = ResponseEntity.ok().body(ratings);

    return response;
  }

  @PostMapping("/reset")
  public ResponseEntity<RatingSet> resetRatings(@AuthenticationPrincipal User user) {
    String username = user.getUsername();

    RatingSet ratings = ratingService.resetRatings(username);

    ResponseEntity<RatingSet> response = ResponseEntity.ok().body(ratings);

    return response;
  }
}
