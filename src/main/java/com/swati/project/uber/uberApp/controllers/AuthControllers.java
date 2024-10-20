package com.swati.project.uber.uberApp.controllers;

import com.swati.project.uber.uberApp.dto.DriverDTO;
import com.swati.project.uber.uberApp.dto.OnboardDriverDTO;
import com.swati.project.uber.uberApp.dto.SignupDTO;
import com.swati.project.uber.uberApp.dto.UserDTO;
import com.swati.project.uber.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthControllers {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignupDTO signupDTO){
        return new ResponseEntity<>(authService.signup(signupDTO), HttpStatus.CREATED);
    }

    @PostMapping("/onBoardNewDriver/{userId}")
    public ResponseEntity<DriverDTO> onBoardNewDriver(@PathVariable Long userId , @RequestBody OnboardDriverDTO onboardDriverDTO){
        return new ResponseEntity<>(authService.onboardNewDriver(userId,onboardDriverDTO.getVehicleId()), HttpStatus.CREATED);
    }
}
