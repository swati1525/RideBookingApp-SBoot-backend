package com.swati.project.uber.uberApp.services.impl;

import com.swati.project.uber.uberApp.dto.DriverDTO;
import com.swati.project.uber.uberApp.dto.SignupDTO;
import com.swati.project.uber.uberApp.dto.UserDTO;
import com.swati.project.uber.uberApp.entities.Driver;
import com.swati.project.uber.uberApp.entities.User;
import com.swati.project.uber.uberApp.entities.enums.Role;
import com.swati.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.swati.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.swati.project.uber.uberApp.repositories.UserRepository;
import com.swati.project.uber.uberApp.services.AuthService;
import com.swati.project.uber.uberApp.services.DriverService;
import com.swati.project.uber.uberApp.services.RiderService;
import com.swati.project.uber.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.swati.project.uber.uberApp.entities.enums.Role.DRIVER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;


    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public UserDTO signup(SignupDTO signupDTO) {
        User user = userRepository.findByEmail(signupDTO.getEmail()).orElseThrow(null);
        if(user!=null){
            throw new RuntimeException("Cannot signup, User already exists with email" + signupDTO.getEmail());
        }


        User mappedUser = modelMapper.map(signupDTO,User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(mappedUser);

        //just after creation of User it should other user related entities like waller and Rider object
        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser,UserDTO.class);

    }

    @Override
    public DriverDTO onboardNewDriver(Long userId, String vehicleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id "+userId));
        if(user.getRoles().contains(DRIVER)){
            throw new RuntimeConflictException("User with id "+userId+" is already a Driver");
        }
        user.getRoles().add(DRIVER);
        userRepository.save(user);

        Driver createDriver = Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();

        Driver saveDriver = driverService.createNewDriver(createDriver);

        return modelMapper.map(saveDriver,DriverDTO.class);
    }
}
