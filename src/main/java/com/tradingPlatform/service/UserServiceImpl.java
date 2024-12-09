package com.tradingPlatform.service;

import com.tradingPlatform.config.JwtProvider;
import com.tradingPlatform.domain.VerificationType;
import com.tradingPlatform.model.TwoFactorAuth;
import com.tradingPlatform.model.TwoFactorOTP;
import com.tradingPlatform.model.User;
import com.tradingPlatform.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@SuppressWarnings("ALL")
@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        log.info("findUserProfileByJwt() ::: start ", this);
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user == null) throw new Exception("User Not Found");

        log.info(" findUserProfileByJwt() ::: end", this);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        log.info("findUserByEmail() ::: start", this);
        User user = userRepository.findByEmail(email);

        if (user == null) throw new Exception("User not found by email");

        log.info("findUserByEmail() ::: ends", this);
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        log.info("findUserById() ::: starts", this);

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new Exception("User Not Found By UserId");

        log.info("findUserById() ::: ends", this);
        return user.get();
    }

    @SuppressWarnings("LoggingPlaceholderCountMatchesArgumentCount")
    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) throws Exception {
        log.info("enableTwoFactorAuthentication() ::: starts" , this);
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);

        user.setTwoFactorAuth(twoFactorAuth);

        log.info("enableTwoFactorAuthentication() ::: ends", this);
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user, String newPassword) throws Exception {
        log.info("updatePassword() ::: starts", this);

        user.setPassword(newPassword);

        log.info("updatePassword() ::: ends", this);
        return userRepository.save(user);
    }
}
