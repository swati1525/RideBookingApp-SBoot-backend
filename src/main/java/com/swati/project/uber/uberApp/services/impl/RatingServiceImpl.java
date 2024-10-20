package com.swati.project.uber.uberApp.services.impl;

import com.swati.project.uber.uberApp.dto.DriverDTO;
import com.swati.project.uber.uberApp.dto.RiderDTO;
import com.swati.project.uber.uberApp.entities.Driver;
import com.swati.project.uber.uberApp.entities.Rating;
import com.swati.project.uber.uberApp.entities.Ride;
import com.swati.project.uber.uberApp.entities.Rider;
import com.swati.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.swati.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.swati.project.uber.uberApp.repositories.DriverRepository;
import com.swati.project.uber.uberApp.repositories.RatingRepository;
import com.swati.project.uber.uberApp.repositories.RiderRepository;
import com.swati.project.uber.uberApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Override
    public DriverDTO rateDriver(Ride ride, Double rating) {
        Driver driver = ride.getDriver();
        //creating object to get rating for each of the ride
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(()-> new ResourceNotFoundException("Rating not found for ride with id: "+ride.getId()));


        if(ratingObj.getDriverRating()!=null){
            throw new RuntimeConflictException("Driver has already been rated, can't rate again");
        }
        ratingObj.setDriverRating(rating);

        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average().orElse(0.0);

        driver.setRating(newRating);

        Driver saveDriver = driverRepository.save(driver);
        return modelMapper.map(saveDriver,DriverDTO.class);
    }

    @Override
    public RiderDTO rateRider(Ride ride, Double rating) {
        Rider rider = ride.getRider();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(()-> new ResourceNotFoundException("Rating not found for ride with id: "+ride.getId()));

        if(ratingObj.getRiderRating()!=null){
            throw new RuntimeConflictException("Rider has already been rated, can't rate again");
        }
        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)
                .average().orElse(0.0);

        rider.setRating(newRating);

        Rider savedRider = riderRepository.save(rider);
        return modelMapper.map(savedRider,RiderDTO.class);

    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .driver(ride.getDriver())
                .rider(ride.getRider())
                .ride(ride)
                .build();

        ratingRepository.save(rating);

    }
}
