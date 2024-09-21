package com.tradingPlatform.service;

import com.tradingPlatform.domain.VerificationType;
import com.tradingPlatform.model.User;
import com.tradingPlatform.model.VerificationCode;
import com.tradingPlatform.repository.VerificationCodeRepository;
import com.tradingPlatform.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService{

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOTP());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);

        return verificationCodeRepository.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);
        if (verificationCode.isPresent()) return verificationCode.get();
        throw new Exception("Verification Code Not Found");
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) throws Exception {
        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
    }

}
