package com.tradingPlatform.servicePaymentDetails;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.tradingPlatform.domain.PaymentMethod;
import com.tradingPlatform.model.User;
import com.tradingPlatform.modelPaymentDetails.PaymentOrder;
import com.tradingPlatform.response.PaymentResponse;

public interface PaymentService {

    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;
    PaymentResponse createRazorpayPaymentLink(User user, Long amount) throws RazorpayException;
    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;

}
