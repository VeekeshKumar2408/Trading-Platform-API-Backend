package com.tradingPlatform.service;

import com.tradingPlatform.domain.VerificationType;
import com.tradingPlatform.model.User;
import com.tradingPlatform.model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType) throws Exception;
    VerificationCode getVerificationCodeById(Long id) throws Exception;
    VerificationCode getVerificationCodeByUser(Long userId) throws Exception;
    void deleteVerificationCodeById(VerificationCode verificationCode);
}
