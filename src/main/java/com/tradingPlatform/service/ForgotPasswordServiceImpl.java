package com.tradingPlatform.service;

import com.tradingPlatform.domain.VerificationType;
import com.tradingPlatform.model.ForgotPasswordToken;
import com.tradingPlatform.model.User;
import com.tradingPlatform.repository.ForgetPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    @Autowired
    private ForgetPasswordRepository forgetPasswordRepository;

    @Override
    public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
        ForgotPasswordToken token = new ForgotPasswordToken();
        token.setUser(user);
        token.setSendTo(sendTo);
        token.setVerificationType(verificationType);
        token.setId(id);
        return forgetPasswordRepository.save(token);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        Optional<ForgotPasswordToken> token = forgetPasswordRepository.findById(id);
        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long userId) {
        return forgetPasswordRepository.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
        forgetPasswordRepository.delete(token);
    }
}
