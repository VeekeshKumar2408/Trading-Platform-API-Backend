package com.tradingPlatform.modelPaymentDetails;

import com.tradingPlatform.domain.PaymentMethod;
import com.tradingPlatform.domain.PaymentOrderStatus;
import com.tradingPlatform.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentOrderStatus status;

    private PaymentMethod paymentMethod;

    @ManyToOne
    private User user;
}
