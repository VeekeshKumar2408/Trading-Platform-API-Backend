package com.tradingPlatform.controller;

import com.tradingPlatform.request.ForgotPasswordTokenRequest;
import com.tradingPlatform.domain.VerificationType;
import com.tradingPlatform.model.ForgotPasswordToken;
import com.tradingPlatform.model.User;
import com.tradingPlatform.model.VerificationCode;
import com.tradingPlatform.request.ResetPasswordRequest;
import com.tradingPlatform.response.ApiResponse;
import com.tradingPlatform.response.AuthResponse;
import com.tradingPlatform.service.EmailService;
import com.tradingPlatform.service.ForgotPasswordService;
import com.tradingPlatform.service.UserService;
import com.tradingPlatform.service.VerificationCodeService;
import com.tradingPlatform.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;
    private String jwt;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/api/user/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/user/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt,
                                                    @PathVariable VerificationType verificationType) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        if (verificationCode == null){
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }

        if (verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }
        return new ResponseEntity<>("OTP Sent Successfully ",HttpStatus.OK);
    }

    @PatchMapping("/api/user/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@RequestHeader("Authorization") String jwt,
                                                              @PathVariable String otp) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL) ? verificationCode.getEmail() : verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);

        if (isVerified) {
            User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo, user);

            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        throw new Exception("Wrong OTP");
    }

    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOTP(@RequestBody ForgotPasswordTokenRequest req,
                                                         @PathVariable VerificationType verificationType) throws Exception{
        User user = userService.findUserByEmail(req.getSendTo());
        String otp = OtpUtils.generateOTP();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());

        if(token == null){
            token = forgotPasswordService.createToken(user, id, otp, req.getVerificationType(), req.getSendTo());
        }

        if (req.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(
                    user.getEmail(),
                    token.getOtp());
        }

        AuthResponse response = new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("OTP For Password Reset Sent Successfully!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id,
                                              @RequestBody ResetPasswordRequest req,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);
        boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());

        if (isVerified){
            userService.updatePassword(forgotPasswordToken.getUser(), req.getPassword());
            ApiResponse res = new ApiResponse();
            res.setMessage("Password Updated Successfully!");
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }
        throw new Exception("Wrong OTP");
    }





}
