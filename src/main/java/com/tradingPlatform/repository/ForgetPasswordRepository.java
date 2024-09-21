package com.tradingPlatform.repository;

import com.tradingPlatform.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<ForgotPasswordToken, String> {

    ForgotPasswordToken findByUserId(Long userId);
}
