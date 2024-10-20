package com.swati.project.uber.uberApp.dto;

import com.swati.project.uber.uberApp.entities.Ride;
import com.swati.project.uber.uberApp.entities.Wallet;
import com.swati.project.uber.uberApp.entities.enums.TransactionMethod;
import com.swati.project.uber.uberApp.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class WalletTransactionDTO {

    private Long id;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private Ride ride;

    private String transactionId;

    private WalletDTO wallet;     //each transaction must have wallet id in the db bcoz each transaction belongs to some wallet either driver's or rider's wallet

    private LocalDateTime timeStamp;
}
