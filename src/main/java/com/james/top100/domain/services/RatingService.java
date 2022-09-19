package com.james.top100.domain.services;

import com.james.top100.domain.types.RatingSetData;
import com.james.top100.infrastructure.entities.RatingSet;
import com.james.top100.infrastructure.repositories.RatingRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class RatingService {
  @Autowired RatingRepository ratingRepository;

  public RatingSet getRatings(String username) {
    Optional<RatingSet> ratings = ratingRepository.findById(username);

    RatingSet ratingSet = ratings.get();

    return ratingSet;
  }

  public RatingSet updateRatings(String username, RatingSetData ratingSetData) {
    RatingSet ratingSet = new RatingSet(username, ratingSetData);

    RatingSet savedRatingSet = ratingRepository.save(ratingSet);

    return savedRatingSet;
  }

  public RatingSet resetRatings(String username) {
    RatingSetData ratingSetData = new RatingSetData();

    RatingSet savedRatingSet = updateRatings(username, ratingSetData);

    return savedRatingSet;
  }
}
