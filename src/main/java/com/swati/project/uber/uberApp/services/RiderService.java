package com.swati.project.uber.uberApp.services;

import com.swati.project.uber.uberApp.dto.DriverDTO;
import com.swati.project.uber.uberApp.dto.RideDTO;
import com.swati.project.uber.uberApp.dto.RideRequestDTO;
import com.swati.project.uber.uberApp.dto.RiderDTO;
import com.swati.project.uber.uberApp.entities.Rider;
import com.swati.project.uber.uberApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RiderService {

    RideRequestDTO requestRide(RideRequestDTO rideRequestDTO );

    RideDTO cancelRide(Long rideId);

    DriverDTO rateDriver(Long rideId, Double rating);

    RiderDTO getMyProfile();

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    Rider createNewRider(User user);

    Rider getCurrentRider();

}
