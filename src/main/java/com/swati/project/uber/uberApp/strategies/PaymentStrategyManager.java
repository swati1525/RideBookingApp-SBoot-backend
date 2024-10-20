package com.swati.project.uber.uberApp.strategies;

import com.swati.project.uber.uberApp.entities.Payment;
import com.swati.project.uber.uberApp.entities.enums.PaymentMethod;
import com.swati.project.uber.uberApp.strategies.impl.CashPaymentStrategy;
import com.swati.project.uber.uberApp.strategies.impl.WalletPayementStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPayementStrategy walletPayementStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch(paymentMethod){
            case WALLET -> walletPayementStrategy;
            case CASH -> cashPaymentStrategy;
        };

    }

}
