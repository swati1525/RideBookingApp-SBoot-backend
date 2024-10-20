package com.swati.project.uber.uberApp.strategies;

import com.swati.project.uber.uberApp.entities.RideRequest;

public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER = 10; //We can put this inside our environment variables. How?

    double calculateFare(RideRequest rideRequest);
}
