
/*
 *
 *  * Copyright (c) Crio.Do 2019. All rights reserved
 *
 */

package com.crio.qeats.services;

import com.crio.qeats.dto.Restaurant;
import com.crio.qeats.exchanges.GetRestaurantsRequest;
import com.crio.qeats.exchanges.GetRestaurantsResponse;
import com.crio.qeats.repositoryservices.RestaurantRepositoryService;
import com.crio.qeats.repositoryservices.RestaurantRepositoryServiceDummyImpl;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RestaurantServiceImpl implements RestaurantService {

  private final Double peakHoursServingRadiusInKms = 3.0;
  private final Double normalHoursServingRadiusInKms = 5.0;
  // LocalTime t8am = LocalTime.parse("08:00:00");
  // LocalTime t10am = LocalTime.parse("09:00:00");
  // LocalTime t1pm = LocalTime.parse("13:00:00");
  // LocalTime t2pm = LocalTime.parse("14:00:00");
  // LocalTime t7pm = LocalTime.parse("19:00:00");
  // LocalTime t9pm = LocalTime.parse("21:00:00");
  @Autowired
  private RestaurantRepositoryService restaurantRepositoryService;


  // TODO: CRIO_TASK_MODULE_RESTAURANTSAPI - Implement findAllRestaurantsCloseby.
  // Check RestaurantService.java file for the interface contract.
  @Override
  public GetRestaurantsResponse findAllRestaurantsCloseBy(
      GetRestaurantsRequest getRestaurantsRequest, LocalTime currentTime) {
      double servingRadius= isPeakhour(currentTime) ? peakHoursServingRadiusInKms : normalHoursServingRadiusInKms;
      List<Restaurant> response=restaurantRepositoryService.findAllRestaurantsCloseBy(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), currentTime,servingRadius);
      GetRestaurantsResponse rv=new GetRestaurantsResponse();
      rv.restaurants=response;
      return rv;
  }

  public boolean isPeakhour(LocalTime currentTime){
    int h =currentTime.getHour();
    int m=currentTime.getMinute();
    if((h>=8 &&h <=9)||(h==10 && m==0)||(h==13)||(h==14 && m==0)||(h>=19 && h<=20)||(h==21 && m==0)){
      return true;
    }
    else return false;
    //return (currentTime.compareTo(t8am)>=0&&currentTime.compareTo(t10am)<=0)||(currentTime.compareTo(t1pm)>=0&&currentTime.compareTo(t2pm)<=0)||(currentTime.compareTo(t7pm)>=0&&currentTime.compareTo(t9pm)<=0);
  }


}

