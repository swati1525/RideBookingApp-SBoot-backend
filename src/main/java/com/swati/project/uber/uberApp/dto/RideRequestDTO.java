package com.swati.project.uber.uberApp.dto;

import com.swati.project.uber.uberApp.entities.enums.PaymentMethod;
import com.swati.project.uber.uberApp.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDTO {

    private Long  id;

    private PointDTO pickUpLocation;           //these three things we need from the user
    private PointDTO dropOffLocation;
    private PaymentMethod paymentMethod;

    private LocalDateTime requestedTime;

    private RiderDTO rider;

    private Double fare;

    private RideRequestStatus rideRequestStatus;
}
