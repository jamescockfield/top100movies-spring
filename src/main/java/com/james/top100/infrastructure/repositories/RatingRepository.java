package com.james.top100.infrastructure.repositories;

import com.james.top100.infrastructure.entities.RatingSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<RatingSet, String> {
  // @Autowired Database database;

  // public RatingSet getRatings(Integer userId) {
  //   return new RatingSet();
  // }

  // public RatingSet updateRatings(Integer userId, UpdateRatingRequest updateRatingRequest) {
  //   return new RatingSet();
  // }

  // public RatingSet resetRatings(Integer userId) {
  //   return new RatingSet();
  // }
}
