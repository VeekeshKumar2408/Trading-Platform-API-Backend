package com.tradingPlatform.modelBTC;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cryptocurrency")
@Data
public class Coin {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("symbol")
    @Column(name = "symbol")
    private String symbol;

    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonProperty("image")
    @Column(name = "image")
    private String image;

    @JsonProperty("current_price")
    @Column(name = "current_price")
    private double currentPrice;

    @JsonProperty("market_cap")
    @Column(name = "market_cap")
    private long marketCap;

    @JsonProperty("market_cap_rank")
    @Column(name = "market_cap_rank")
    private int marketCapRank;

    @JsonProperty("fully_diluted_valuation")
    @Column(name = "fully_diluted_valuation")
    private long fullyDilutedValuation;

    @JsonProperty("total_volume")
    @Column(name = "total_volume")
    private long totalVolume;

    @JsonProperty("high_24h")
    @Column(name = "high_24h")
    private double high24h;

    @JsonProperty("low_24h")
    @Column(name = "low_24h")
    private double low24h;

    @JsonProperty("price_change_24h")
    @Column(name = "price_change_24h")
    private double priceChange24h;

    @JsonProperty("price_change_percentage_24h")
    @Column(name = "price_change_percentage_24h")
    private double priceChangePercentage24h;

    @JsonProperty("market_cap_change_24h")
    @Column(name = "market_cap_change_24h")
    private long marketCapChange24h;

    @JsonProperty("market_cap_change_percentage_24h")
    @Column(name = "market_cap_change_percentage_24h")
    private double marketCapChangePercentage24h;

    @JsonProperty("circulating_supply")
    @Column(name = "circulating_supply")
    private double circulatingSupply;

    @JsonProperty("total_supply")
    @Column(name = "total_supply")
    private double totalSupply;

    @JsonProperty("max_supply")
    @Column(name = "max_supply")
    private Double maxSupply; // Nullable

    @JsonProperty("ath")
    @Column(name = "ath")
    private double ath;

    @JsonProperty("ath_change_percentage")
    @Column(name = "ath_change_percentage")
    private double athChangePercentage;

    @JsonProperty("ath_date")
    @Column(name = "ath_date")
    private String athDate;

    @JsonProperty("atl")
    @Column(name = "atl")
    private double atl;

    @JsonProperty("atl_change_percentage")
    @Column(name = "atl_change_percentage")
    private double atlChangePercentage;

    @JsonProperty("atl_date")
    @Column(name = "atl_date")
    private String atlDate;

    @JsonProperty("roi")
    @JsonIgnore
    private String roi;

    @JsonProperty("last_updated")
    @Column(name = "last_updated")
    private String lastUpdated;

}

