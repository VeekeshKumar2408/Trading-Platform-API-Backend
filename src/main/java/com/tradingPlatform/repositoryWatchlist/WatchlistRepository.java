package com.tradingPlatform.repositoryWatchlist;

import com.tradingPlatform.modelWatchlist.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    Watchlist findByUserId(Long userId);
}
