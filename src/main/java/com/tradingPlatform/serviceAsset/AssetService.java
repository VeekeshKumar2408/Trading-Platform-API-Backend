package com.tradingPlatform.serviceAsset;

import com.tradingPlatform.model.User;
import com.tradingPlatform.modelAsset.Asset;
import com.tradingPlatform.modelBTC.Coin;

import java.util.List;

public interface AssetService {

    Asset createAsset(User user, Coin coin, double quantity);

    Asset getAssetById(Long assetId) throws Exception;

    Asset getAssetByUserIdAndId(Long userId, Long assetId);

    List<Asset> getUserAssets(Long userId) throws Exception;

    Asset updateAsset(Long assetId, double quantity) throws Exception;

    Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

    void deleteAsset(Long assetId);
}
