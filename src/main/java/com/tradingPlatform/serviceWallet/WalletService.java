package com.tradingPlatform.serviceWallet;

import com.tradingPlatform.model.User;
import com.tradingPlatform.modelWallet.Order;
import com.tradingPlatform.modelWallet.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long amt);
    Wallet findWalletById(Long id);
    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amt);
    Wallet payOrderPayment(Order order, User user);


}
