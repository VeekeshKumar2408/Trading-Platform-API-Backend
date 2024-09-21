package com.tradingPlatform.serviceWallet;

import com.tradingPlatform.model.User;
import com.tradingPlatform.modelWallet.Order;
import com.tradingPlatform.modelWallet.Wallet;

public class WalletServiceImpl implements WalletService{
    @Override
    public Wallet getUserWallet(User user) {
        return null;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long amt) {
        return null;
    }

    @Override
    public Wallet findWalletById(Long id) {
        return null;
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amt) {
        return null;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) {
        return null;
    }
}
