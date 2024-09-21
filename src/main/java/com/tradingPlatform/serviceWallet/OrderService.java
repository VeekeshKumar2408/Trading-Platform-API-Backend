package com.tradingPlatform.serviceWallet;

import com.tradingPlatform.domain.OrderType;
import com.tradingPlatform.model.User;
import com.tradingPlatform.modelBTC.Coin;
import com.tradingPlatform.modelWallet.Order;
import com.tradingPlatform.modelWallet.OrderItem;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order getOrderById(Long orderId) throws Exception;
    List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);
    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}
