package com.swati.project.uber.uberApp.dto;


import com.swati.project.uber.uberApp.entities.enums.PaymentMethod;
import com.swati.project.uber.uberApp.entities.enums.RideStatus;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
public class RideDTO {

    private Long  id;
    private PointDTO pickUpLocation;
    private PointDTO dropOffLocation;
    private LocalDateTime createdTime; //Time when driver accepts your ride
    private RiderDTO rider;
    private DriverDTO driver;
    private PaymentMethod paymentMethod;
    private RideStatus rideStatus;

    private String otp;

    private Double fare;
    private LocalDateTime startedAt; //time when ride starts once driver enters the otp
    private LocalDateTime endedAt; //time when ride ends
}
