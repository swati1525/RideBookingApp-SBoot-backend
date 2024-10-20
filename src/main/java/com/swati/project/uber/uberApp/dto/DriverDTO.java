package com.swati.project.uber.uberApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data    //is the combination @Getter and @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

    private Long id;
    private UserDTO user;
    private Double rating;
    private Boolean available;
    private String vehicleId;

}
