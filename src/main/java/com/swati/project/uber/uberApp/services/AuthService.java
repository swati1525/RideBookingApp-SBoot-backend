package com.swati.project.uber.uberApp.services;

import com.swati.project.uber.uberApp.dto.DriverDTO;
import com.swati.project.uber.uberApp.dto.SignupDTO;
import com.swati.project.uber.uberApp.dto.UserDTO;

public interface AuthService {
    String login(String email, String password);

    UserDTO signup(SignupDTO signupDTO);

    DriverDTO onboardNewDriver(Long userId, String vehicleId);
}
