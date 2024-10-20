package com.swati.project.uber.uberApp.strategies;

import com.swati.project.uber.uberApp.strategies.impl.DriverMatchingHigestRatedDriverStrategy;
import com.swati.project.uber.uberApp.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.swati.project.uber.uberApp.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import com.swati.project.uber.uberApp.strategies.impl.RiderFareDefaultFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {  //any time you want to use any strategy like DriverMatchingStrategy or RideFareCalculationStrategy you are going to come to this manager

    private final DriverMatchingHigestRatedDriverStrategy highestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;
    private final RiderFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating){
        if(riderRating > 4.8){
            return highestRatedDriverStrategy;
        }else{
            return nearestDriverStrategy;
        }

    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){

        //6PM to 9PM
        LocalTime surgeStartTime= LocalTime.of(18,0);
        LocalTime surgeEndTime = LocalTime.of(21,0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime){
            return surgePricingFareCalculationStrategy;
        }else{
            return defaultFareCalculationStrategy;
        }
    }
}
