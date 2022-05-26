package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Wallet;

public interface WalletService {
    Wallet save(Wallet wallet);
    Wallet getById(Long id);
}
