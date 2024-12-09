package com.tradingPlatform.repositoryPaymentDetails;

import com.tradingPlatform.modelPaymentDetails.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,Long> {
}
