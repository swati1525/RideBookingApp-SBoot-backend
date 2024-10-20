package com.swati.project.uber.uberApp.services;

import com.swati.project.uber.uberApp.entities.Ride;
import com.swati.project.uber.uberApp.entities.User;
import com.swati.project.uber.uberApp.entities.Wallet;
import com.swati.project.uber.uberApp.entities.enums.TransactionMethod;
import com.swati.project.uber.uberApp.entities.enums.TransactionType;

public interface WalletService {

    Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    Wallet createNewWallet(User user);

    void withdrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet findByUser(User user);

}
