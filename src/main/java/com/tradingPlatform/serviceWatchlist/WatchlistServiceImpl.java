package com.tradingPlatform.serviceWatchlist;

import com.tradingPlatform.model.User;
import com.tradingPlatform.modelBTC.Coin;
import com.tradingPlatform.modelWatchlist.Watchlist;
import com.tradingPlatform.repositoryBTC.CoinRepository;
import com.tradingPlatform.repositoryWatchlist.WatchlistRepository;
import com.tradingPlatform.serviceBTC.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchlistServiceImpl implements WatchlistService{

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private CoinService coinService;

    @Autowired
    private CoinRepository coinRepository;

    @Override
    public Watchlist findUserWatchlist(Long userId) throws Exception {
        Watchlist watchlist = watchlistRepository.findByUserId(userId);
        if (watchlist == null){
            throw new Exception("Watchlist not found");
        }
        return watchlist;
    }

    @Override
    public Watchlist createWatchList(User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        return watchlistRepository.save(watchlist);
    }

    @Override
    public Watchlist findById(Long id) throws Exception {
        Optional<Watchlist> watchlistOptional = watchlistRepository.findById(id);
        if (watchlistOptional.isEmpty()){
            throw new Exception("Watchlist Not Found");
        }
        return watchlistOptional.get();
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
        Watchlist watchlist = findUserWatchlist(user.getId());

        if (watchlist.getCoins().contains(coin)) watchlist.getCoins().remove(coin);
        else watchlist.getCoins().add(coin);

        watchlistRepository.save(watchlist);
        return coin;
    }
}
