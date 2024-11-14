package com.hhhh.dodream.global.common.enums;

import lombok.Getter;

@Getter
public enum KeyPrefixEnum {
    REFRESH("Refresh:"),
    LOGOUT_ACCESS("LogoutAccess:");

    private final String keyPrefix;

    KeyPrefixEnum(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

}
