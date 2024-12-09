package com.tradingPlatform.servicePaymentDetails;

import com.tradingPlatform.model.User;
import com.tradingPlatform.modelPaymentDetails.PaymentDetails;

public interface PaymentDetailsService {
     PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName, User user);
     PaymentDetails getUsersPaymentDetails(User user);

}
