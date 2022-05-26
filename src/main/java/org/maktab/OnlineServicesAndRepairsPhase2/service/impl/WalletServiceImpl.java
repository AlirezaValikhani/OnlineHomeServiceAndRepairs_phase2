package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Wallet;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundWalletException;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.WalletRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.WalletService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet getById(Long id) {
        return walletRepository.getById(id);
    }
}
