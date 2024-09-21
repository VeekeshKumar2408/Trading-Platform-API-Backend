package com.tradingPlatform.serviceWallet;

import com.tradingPlatform.model.User;
import com.tradingPlatform.modelWallet.Withdrawal;
import lombok.With;

import java.util.List;

public interface WithdrawalService {

    Withdrawal requestWithdrawal(Long amount, User user);
    Withdrawal proceedWithdrawal(Long withdrawalId, boolean accept) throws Exception;
    List<Withdrawal> getUserWithdrawalHistory(User user);
    List<Withdrawal> getAllWithdrawalRequest();
}
