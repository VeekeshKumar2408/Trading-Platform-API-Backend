package com.tradingPlatform.serviceAsset;

import com.tradingPlatform.model.User;
import com.tradingPlatform.modelAsset.Asset;
import com.tradingPlatform.modelBTC.Coin;
import com.tradingPlatform.repositoryAsset.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService{

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        try{
            Asset asset = new Asset();
            asset.setUser(user);
            asset.setCoin(coin);
            asset.setQuantity(quantity);
            asset.setBuyPrice(coin.getCurrentPrice());
            return assetRepository.save(asset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Asset getAssetById(Long assetId) throws Exception {

        return assetRepository.findById(assetId).orElseThrow(
                ()-> new Exception("Asset Not Found")
        );
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUserAssets(Long userId) throws Exception {
        try {
            return assetRepository.findByUserId(userId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception {
        try {
            Asset oldAsset = getAssetById(assetId);
            oldAsset.setQuantity(quantity + oldAsset.getQuantity());
            return assetRepository.save(oldAsset);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        try{
            return assetRepository.findByUserIdAndCoinId(userId, coinId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAsset(Long assetId) {
        try{
            assetRepository.deleteById(assetId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
