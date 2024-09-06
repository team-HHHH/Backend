package com.hhhh.dodream.global.common.enums;

import lombok.Getter;

@Getter
public enum RedisKeyPrefixEnum {
    REFRESH("Refresh:");

    private final String description;

    RedisKeyPrefixEnum(String description) {
        this.description = description;
    }

}
