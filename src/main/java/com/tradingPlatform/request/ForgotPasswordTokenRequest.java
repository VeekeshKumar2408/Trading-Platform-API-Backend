package com.tradingPlatform.request;

import com.tradingPlatform.domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
