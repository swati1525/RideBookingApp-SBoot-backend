package com.swati.project.uber.uberApp.strategies.impl;

import com.swati.project.uber.uberApp.entities.*;
import com.swati.project.uber.uberApp.entities.enums.PaymentStatus;
import com.swati.project.uber.uberApp.entities.enums.TransactionMethod;
import com.swati.project.uber.uberApp.entities.enums.TransactionType;
import com.swati.project.uber.uberApp.repositories.PaymentRepository;
import com.swati.project.uber.uberApp.services.PaymentService;
import com.swati.project.uber.uberApp.services.WalletService;
import com.swati.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

//Rider -> 100
//Driver -> 70 Deduct 100Rs from Driver's Wallet
@Service
@AllArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        double platformCommission = payment.getAmount()*PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission, null, payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
