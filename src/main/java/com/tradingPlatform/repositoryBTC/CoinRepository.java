package com.tradingPlatform.repositoryBTC;

import com.tradingPlatform.modelBTC.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<Coin, String> {
}
