package com.james.top100.domain.services;

import java.util.Date;

public class DateService {
  public Date getDateNow() {
    Date currentDate = new Date();

    return currentDate;
  }

  public Date getDateInPast(Integer msInPast) {
    Date pastDate = new Date(new Date().getTime() - msInPast);

    return pastDate;
  }

  public Date getDateInFuture(Integer msInFuture) {
    Date futureDate = new Date(new Date().getTime() + msInFuture);

    return futureDate;
  }
}
