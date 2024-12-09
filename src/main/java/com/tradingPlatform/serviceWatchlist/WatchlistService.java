package com.tradingPlatform.serviceWatchlist;

import com.tradingPlatform.model.User;
import com.tradingPlatform.modelBTC.Coin;
import com.tradingPlatform.modelWatchlist.Watchlist;

public interface WatchlistService {

    Watchlist findUserWatchlist(Long userId) throws Exception;
    Watchlist createWatchList(User user);
    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin, User user) throws Exception;
}
