package com.eterna.backend.core.auth.domain.objects;

import java.util.UUID;

public final class OtpCode {
    private final String value;

    public OtpCode(String otpCode) {
        this.value = otpCode;
    }

    public String getValue() {
        return value;
    }

    public String getKey(UUID userId) {
        return "activation:otp:" + userId;
    }

    public static OtpCode of(String otpCode) {
        return new OtpCode(otpCode);
    }

}
