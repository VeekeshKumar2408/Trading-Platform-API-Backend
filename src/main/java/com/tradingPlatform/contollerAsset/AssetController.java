package com.tradingPlatform.contollerAsset;

import com.tradingPlatform.model.User;
import com.tradingPlatform.modelAsset.Asset;
import com.tradingPlatform.service.UserService;
import com.tradingPlatform.serviceAsset.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserService userService;

    @GetMapping("/{assetId}")
    public ResponseEntity<?> getAssetById(@PathVariable Long assetId) throws Exception {
        try {
            Asset asset = assetService.getAssetById(assetId);
            if (asset != null) return ResponseEntity.ok().body(asset);
            else return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<?> getAssetByUseridAndCoinId(@PathVariable String coinId, @RequestHeader("Authorization") String jwt) throws Exception {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);
            if (asset != null) return ResponseEntity.ok().body(asset);
            else return ResponseEntity.notFound().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error Occured");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAssetsForUser(@RequestHeader("Authorization") String jwt){
        try{
            User user = userService.findUserProfileByJwt(jwt);
            List<Asset> assets = assetService.getUserAssets(user.getId());
            if(assets!=null) return ResponseEntity.ok().body(assets);
            else return ResponseEntity.badRequest().body("Bad Request");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
