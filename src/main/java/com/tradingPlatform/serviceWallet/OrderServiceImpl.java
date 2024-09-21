package com.tradingPlatform.serviceWallet;

import com.tradingPlatform.domain.OrderStatus;
import com.tradingPlatform.domain.OrderType;
import com.tradingPlatform.model.User;
import com.tradingPlatform.modelAsset.Asset;
import com.tradingPlatform.modelBTC.Coin;
import com.tradingPlatform.modelWallet.Order;
import com.tradingPlatform.modelWallet.OrderItem;
import com.tradingPlatform.repositoryWallet.OrderItemRepository;
import com.tradingPlatform.repositoryWallet.OrderRepository;
import com.tradingPlatform.serviceAsset.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AssetService assetService;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        try {
            double price = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();
            Order order = new Order();
            order.setUser(user);
            order.setOrderItem(orderItem);
            order.setOrderType(orderType);
            order.setPrice(BigDecimal.valueOf(price));
            order.setTimeStamp(LocalDateTime.now());
            order.setStatus(OrderStatus.PENDING);

            return orderRepository.save(order);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId)
                .orElseThrow(
                        ()-> new Exception("Order Not Found")
                );
    }

    @Override
    public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice){
        try {
            OrderItem orderItem = new OrderItem();
            orderItem.setCoin(coin);
            orderItem.setQuantity(quantity);
            orderItem.setBuyPrice(buyPrice);
            orderItem.setSellPrice(sellPrice);

            return orderItemRepository.save(orderItem);
        } catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public Order buyAsset(Coin coin, double quantity, User user) throws Exception {
        if (quantity <=0){
            throw new Exception("Quantity Should Be Greater Than Zero");
        }
        double buyPrice = coin.getCurrentPrice();

        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);
        Order order = createOrder(user, orderItem, OrderType.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order, user);

        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);

        Order savedOrder = orderRepository.save(order);

        // create asset
        Asset oldAsset = assetService.findAssetByUserIdAndCoinId(order.getUser().getId(), order.getOrderItem().getCoin().getId());
        if(oldAsset==null){
            assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
        }else{
            assetService.updateAsset(oldAsset.getId(), quantity);
        }

        return savedOrder;
    }

    public Order sellAsset(Coin coin, double quantity, User user) throws Exception{
        if (quantity <= 0){
            throw new Exception("Quantity Should Be Greater Than Zero");
        }

        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());
        double buyPrice = assetToSell.getBuyPrice();
        double sellPrice = coin.getCurrentPrice();

        if(assetToSell!=null){
            OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
            Order order = createOrder(user, orderItem, OrderType.SELL);
            orderItem.setOrder(order);

            if(assetToSell.getQuantity() >= quantity) {
                order.setStatus(OrderStatus.SUCCESS);
                order.setOrderType(OrderType.SELL);
                Order savedOrder = orderRepository.save(order);

                walletService.payOrderPayment(order, user);
                Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);

                if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
                    assetService.deleteAsset(updatedAsset.getId());
                }
                return savedOrder;
            }
            throw new Exception("Insufficient Quantity To Sell");
        }
        throw new Exception("Asset Not Found! ");
    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {

        if (orderType.equals(OrderType.BUY)){
            return buyAsset(coin,quantity,user);
        } else if (orderType.equals(OrderType.SELL)){
            return sellAsset(coin,quantity,user);
        }
        throw new Exception("Invalid Order Type");
    }
}
