package com.tradingPlatform.modelWallet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tradingPlatform.modelBTC.Coin;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double quantity;

    @ManyToOne
    private Coin coin;
    private double buyPrice;
    private double sellPrice;

    @JsonIgnore
    @OneToOne
    private Order order;
}
