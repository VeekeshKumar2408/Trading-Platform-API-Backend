package com.tradingPlatform.repositoryWallet;

import com.tradingPlatform.modelWallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByUserId(Long UserId);
}
