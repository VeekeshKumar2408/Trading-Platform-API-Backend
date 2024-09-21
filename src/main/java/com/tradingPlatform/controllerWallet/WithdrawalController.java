package com.tradingPlatform.controllerWallet;

import com.tradingPlatform.model.User;
import com.tradingPlatform.modelWallet.Wallet;
import com.tradingPlatform.modelWallet.WalletTransaction;
import com.tradingPlatform.modelWallet.Withdrawal;
import com.tradingPlatform.service.UserService;
import com.tradingPlatform.serviceWallet.WalletService;
import com.tradingPlatform.serviceWallet.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;

    @PostMapping("/{amount}")
    public ResponseEntity<?> withdrawalRequest(@PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount,user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());

       // WalletTransaction walletTransaction = new WalletTransactionService.

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @PatchMapping("api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(@PathVariable Long id, @PathVariable Boolean accept, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Withdrawal withdrawal = withdrawalService.proceedWithdrawal(id,accept);

        Wallet userWallet = walletService.getUserWallet(user);
        if (!accept) walletService.addBalance(userWallet, withdrawal.getAmount());

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllWithdrawalRequest(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        List<Withdrawal> withdrawals = withdrawalService.getAllWithdrawalRequest();

        return new ResponseEntity<>(withdrawals, HttpStatus.OK);

    }
}
