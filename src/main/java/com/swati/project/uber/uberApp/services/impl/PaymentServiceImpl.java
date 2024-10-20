package com.swati.project.uber.uberApp.services.impl;

import com.swati.project.uber.uberApp.entities.Payment;
import com.swati.project.uber.uberApp.entities.Ride;
import com.swati.project.uber.uberApp.entities.enums.PaymentStatus;
import com.swati.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.swati.project.uber.uberApp.repositories.PaymentRepository;
import com.swati.project.uber.uberApp.services.PaymentService;
import com.swati.project.uber.uberApp.strategies.PaymentStrategyManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void processPayment(Ride ride) { //how do you want to process this payment. In payment we can have payment stratergy
        Payment payment = paymentRepository.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException("Payment not found for ride with id: "+ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {

        Payment payment = Payment.builder()
                .paymentMethod(ride.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .ride(ride)
                .amount(ride.getFare())
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus){
        payment.setPaymentStatus(paymentStatus);
    }
}
