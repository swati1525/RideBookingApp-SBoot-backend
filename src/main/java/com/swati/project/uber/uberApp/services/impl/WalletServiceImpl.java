package com.swati.project.uber.uberApp.services.impl;

import com.swati.project.uber.uberApp.entities.Ride;
import com.swati.project.uber.uberApp.entities.User;
import com.swati.project.uber.uberApp.entities.Wallet;
import com.swati.project.uber.uberApp.entities.WalletTransaction;
import com.swati.project.uber.uberApp.entities.enums.TransactionMethod;
import com.swati.project.uber.uberApp.entities.enums.TransactionType;
import com.swati.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.swati.project.uber.uberApp.repositories.WalletRepository;
import com.swati.project.uber.uberApp.services.WalletService;
import com.swati.project.uber.uberApp.services.WalletTransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {

        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()+amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()-amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();
        walletTransactionService.createNewWalletTransaction(walletTransaction);
       // wallet.getWalletTransaction().add(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet userWallet = new Wallet();
        userWallet.setUser(user);

        return walletRepository.save(userWallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow(()-> new ResourceNotFoundException("Wallet not found with id : "+walletId));
    }

    @Override
    public Wallet findByUser(User user) {
        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(()->new ResourceNotFoundException("Wallet not found for user with id: "+ user.getId()));
        return walletRepository.save(wallet);
    }
}
