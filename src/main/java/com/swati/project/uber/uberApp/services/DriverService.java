package com.swati.project.uber.uberApp.services;

import com.swati.project.uber.uberApp.dto.DriverDTO;
import com.swati.project.uber.uberApp.dto.RideDTO;
import com.swati.project.uber.uberApp.dto.RiderDTO;
import com.swati.project.uber.uberApp.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface DriverService {

    RideDTO acceptRide(Long rideRequestId);

    RideDTO cancelRide(Long rideId);

    RideDTO startRide(Long rideId, String otp);

    RideDTO endRide(Long rideId);

    RiderDTO rateRider(Long rideId, Double rating);

    DriverDTO getMyProfile();

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    Driver updateDriverAvailability(Driver driver, boolean available);

    Driver getCurrentDriver();

    Driver createNewDriver(Driver driver);
}
