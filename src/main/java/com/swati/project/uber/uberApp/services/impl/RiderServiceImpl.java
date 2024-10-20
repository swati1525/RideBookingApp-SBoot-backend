package com.swati.project.uber.uberApp.services.impl;

import com.swati.project.uber.uberApp.dto.DriverDTO;
import com.swati.project.uber.uberApp.dto.RideDTO;
import com.swati.project.uber.uberApp.dto.RideRequestDTO;
import com.swati.project.uber.uberApp.dto.RiderDTO;
import com.swati.project.uber.uberApp.entities.*;
import com.swati.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.swati.project.uber.uberApp.entities.enums.RideStatus;
import com.swati.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.swati.project.uber.uberApp.repositories.RideRequestRepository;
import com.swati.project.uber.uberApp.repositories.RiderRepository;
import com.swati.project.uber.uberApp.services.*;
import com.swati.project.uber.uberApp.strategies.RideStrategyManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j                      //for logs
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    //Why are you having another service repository(i.e rideRequest repo) inside this
    //We only have one method requestRide to define in service so we didn't create it and directly did it here  BUT EACH SERVICE SHOULD ONLY HAVE ITS OWN REPOSITORY COZ IT IS THE BEST WAY
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final PaymentService paymentService;
    private final RatingService ratingService;

    @Override
    @Transactional  //Maintains ATOMICITY- either everything should happen or nothing should happen
    public RideRequestDTO requestRide(RideRequestDTO rideRequestDTO) {
        Rider rider = getCurrentRider();

        RideRequest rideRequest = modelMapper.map(rideRequestDTO,RideRequest.class);
//        log.info(rideRequest.toString());
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        //We are first calculating the whole fare
        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        //After calculating the fare we are saving the ride request inside the db coz ride request has to be saved no matter if we found the driver or not
        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        //this method is called to fetch all the drivers(this mehod will transmit or broadcast a message to all the drivers who are matched, so we can't to anything with the drivers- now it is upto the divers whether they want to accept the ride or not)
        List<Driver> drivers = rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);
//TODO : Send notification to all the drivers about this ride request

        return modelMapper.map(savedRideRequest,RideRequestDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        Rider currentRider = getCurrentRider();
        Ride currentRide = rideService.getRideById(rideId);

        if(!currentRider.equals(currentRide.getRider())){
            throw new RuntimeException("Rider does not own this ride with id: "+rideId);
        }
        if(!currentRide.equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride can't be cancelled, invalid status: "+ currentRide.getRideStatus());
        }
        Ride savedRide = rideService.updateRideStatus(currentRide,RideStatus.CANCELLED);
        driverService.updateDriverAvailability(currentRide.getDriver(),true);
        return modelMapper.map(savedRide,RideDTO.class);
    }

    @Override
    public DriverDTO rateDriver(Long rideId, Double rating) {

        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider is not the owner of this  Ride");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not Ended hence can't be rated, status: "+ride.getRideStatus());
        }

        return ratingService.rateDriver(ride,rating);
    }

    @Override
    public RiderDTO getMyProfile() {
        Rider rider = getCurrentRider();
        return modelMapper.map(rider.getId(), RiderDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider,pageRequest)
                .map(ride-> modelMapper.map(ride,RideDTO.class));
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();

        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        //TODO : implement Spring Security
        return riderRepository.findById(1L).orElseThrow(()->new ResourceNotFoundException(
                "Rider not found with id: " + 1
        ));
    }

}
