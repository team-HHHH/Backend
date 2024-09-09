package com.hhhh.dodream.global.common.utils;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class RandomUtils {
    private static final SecureRandom secureRandom = new SecureRandom();

    public Integer getAuthCode() {
        return 100000 + secureRandom.nextInt(900000);
    }
}