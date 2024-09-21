package com.tradingPlatform.controller;

import com.tradingPlatform.config.JwtProvider;
import com.tradingPlatform.model.TwoFactorOTP;
import com.tradingPlatform.model.User;
import com.tradingPlatform.repository.UserRepository;
import com.tradingPlatform.response.AuthResponse;
import com.tradingPlatform.service.CustomUserDetailsService;
import com.tradingPlatform.service.EmailService;
import com.tradingPlatform.service.TwoFactorOtpService;
import com.tradingPlatform.utils.OtpUtils;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody User user){

        log.info("Inside register method");
        try {
            User isEmailExist = userRepository.findByEmail(user.getEmail());

            if (isEmailExist != null) throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Already Exits, Try new Email");

            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setFullName(user.getFullName());
            newUser.setMobile(user.getMobile());

            userRepository.save(newUser);


            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = JwtProvider.generateToken(authentication);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt(jwt);
            authResponse.setStatus(true);
            authResponse.setMessage("Signed Up Successfully! ");

            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
       }
        catch (Exception e){
            log.error("Error occurred while creating new User", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> login(@RequestBody User user ) throws Exception {

        log.info("Inside login method :::");

        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication authentication = authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User authUser = userRepository.findByEmail(userName);

        String jwt = JwtProvider.generateToken(authentication);

        if (user.getTwoFactorAuth().isEnabled()){
            AuthResponse response = new AuthResponse();
            response.setMessage("Two Factor Auth Is Enabled");
            response.setTwoFactorAuthEnabled(true);
            /*
            * Generating the OTP
            * */
            String otp = OtpUtils.generateOTP();
            TwoFactorOTP oldTwoFactorOTP = twoFactorOtpService.findByUser(authUser.getId());
            if (oldTwoFactorOTP != null) twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
            TwoFactorOTP newTwoFactorOTP = twoFactorOtpService.createTwoFactorOtp(authUser,otp, jwt);

            emailService.sendVerificationOtpEmail(userName, otp);

            response.setSession(newTwoFactorOTP.getId());
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("sign-in Successful! ");

        return ResponseEntity.status(HttpStatus.OK).body(authResponse);

    }

    /*
    * This method verifies if the userName and password is correct or not
    * */
    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

        if (userDetails == null) throw new BadCredentialsException("Invalid Username");
        if (!password.equals(userDetails.getPassword())) throw new BadCredentialsException("Invalid Password");

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    public ResponseEntity<AuthResponse> verifySignInOtp(
            @PathVariable String otp,
            @RequestParam String id) throws Exception {

        TwoFactorOTP twoFactorOTP = twoFactorOtpService.findById(id);

        if (twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP, otp)){
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Two Factor Authentication verified");
            authResponse.setTwoFactorAuthEnabled(true);
            authResponse.setJwt(twoFactorOTP.getJwt());

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }

        throw new Exception("Invalid Otp");
    }




}
