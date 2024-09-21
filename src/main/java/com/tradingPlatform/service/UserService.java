package com.tradingPlatform.service;

import com.tradingPlatform.domain.VerificationType;
import com.tradingPlatform.model.User;

public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
    User findUserById(Long userId) throws Exception;
    User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user)throws Exception;
    User updatePassword(User user, String newPassword) throws Exception;
}
