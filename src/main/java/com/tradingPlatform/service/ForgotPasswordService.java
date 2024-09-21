package com.tradingPlatform.service;

import com.tradingPlatform.domain.VerificationType;
import com.tradingPlatform.model.ForgotPasswordToken;
import com.tradingPlatform.model.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user, String id,
                                    String otp, VerificationType verificationType,
                                    String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
