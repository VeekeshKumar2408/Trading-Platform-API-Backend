package com.tradingPlatform.controllerWallet;

import com.tradingPlatform.domain.OrderType;
import com.tradingPlatform.model.User;
import com.tradingPlatform.modelBTC.Coin;
import com.tradingPlatform.modelWallet.Order;
import com.tradingPlatform.modelWallet.WalletTransaction;
import com.tradingPlatform.request.CreateOrderRequest;
import com.tradingPlatform.service.UserService;
import com.tradingPlatform.serviceBTC.CoinService;
import com.tradingPlatform.serviceWallet.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

   // @Autowired
  //  private WalletTransactionService walletTransactionService;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest req
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(req.getCoinId());

        Order order = orderService.processOrder(coin, req.getQuantity(), req.getOrderType(), user);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable Long orderId
    ) throws Exception {

        //if(jwtToken == null) throw new Exception("Token Missing!!!");
        User user = userService.findUserProfileByJwt(jwtToken);
        Order order = orderService.getOrderById(orderId);

        if (order.getUser().getId().equals(user.getId())) return ResponseEntity.ok(order);
        else throw new Exception("You Don't Have Access");
    }

    @GetMapping
    public ResponseEntity<?> getAllOrdersForUser(
            @RequestHeader("Authorization") String jwtToken,
            @RequestParam(required = false) OrderType order_type,
            @RequestParam(required = false) String asset_symbol
    ) throws Exception{

        Long userId = userService.findUserProfileByJwt(jwtToken).getId();
        Optional<List<Order>> userOrders = Optional.ofNullable(orderService.getAllOrderOfUser(userId , order_type,asset_symbol));

        if (userOrders.isPresent()) return ResponseEntity.ok(userOrders.get());
        return ResponseEntity.noContent().build();
    }
}
