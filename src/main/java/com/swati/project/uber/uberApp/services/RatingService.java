package com.swati.project.uber.uberApp.services;

import com.swati.project.uber.uberApp.dto.DriverDTO;
import com.swati.project.uber.uberApp.dto.RiderDTO;
import com.swati.project.uber.uberApp.entities.Driver;
import com.swati.project.uber.uberApp.entities.Ride;
import com.swati.project.uber.uberApp.entities.Rider;

public interface RatingService {

   DriverDTO rateDriver(Ride ride, Double rating);
   RiderDTO rateRider(Ride ride, Double rating);

   void createNewRating(Ride ride);
}
