package com.james.top100.infrastructure.entities;

import com.james.top100.domain.types.RatingSetData;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingSet {
  @Id private String username;
  private RatingSetData ratingSetData;
}
