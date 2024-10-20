package com.swati.project.uber.uberApp.services.impl;

import com.swati.project.uber.uberApp.dto.WalletTransactionDTO;
import com.swati.project.uber.uberApp.entities.WalletTransaction;
import com.swati.project.uber.uberApp.repositories.WalletTransactionRepository;
import com.swati.project.uber.uberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;
    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }
}
