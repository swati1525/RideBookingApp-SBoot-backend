package com.swati.project.uber.uberApp.dto;

import com.swati.project.uber.uberApp.entities.User;
import com.swati.project.uber.uberApp.entities.WalletTransaction;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class WalletDTO {

    private Long id;

    private UserDTO user;

    private Double balance;

    private List<WalletTransactionDTO> walletTransaction;
}
