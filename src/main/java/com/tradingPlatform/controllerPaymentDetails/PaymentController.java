package com.tradingPlatform.controllerPaymentDetails;

import com.tradingPlatform.domain.PaymentMethod;
import com.tradingPlatform.model.User;
import com.tradingPlatform.modelPaymentDetails.PaymentOrder;
import com.tradingPlatform.response.PaymentResponse;
import com.tradingPlatform.service.UserService;
import com.tradingPlatform.servicePaymentDetails.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> paymentHandler(@PathVariable PaymentMethod paymentMethod, @PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        PaymentResponse paymentResponse;

        PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse = paymentService.createRazorpayPaymentLink(user, amount);
        } else {
            paymentResponse = paymentService.createStripePaymentLink(user, amount, order.getId());
        }

        return  new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }


}
