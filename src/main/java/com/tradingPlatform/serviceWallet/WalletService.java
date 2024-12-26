package com.tradingPlatform.serviceWallet;

import com.tradingPlatform.model.User;
import com.tradingPlatform.modelWallet.Order;
import com.tradingPlatform.modelWallet.Wallet;

import java.math.BigDecimal;

public interface WalletService {
    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long amt);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amt) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;
    void createWallet(User user, BigDecimal balance);


}
