package com.tradingPlatform.repositoryWallet;

import com.tradingPlatform.modelWallet.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
