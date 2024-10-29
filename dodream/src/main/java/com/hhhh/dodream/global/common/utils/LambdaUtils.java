package com.hhhh.dodream.global.common.utils;


import java.util.function.Consumer;

public class LambdaUtils {

    public static <T> void updateLambda(T data, Consumer<T> consumer) {
        if (data != null) {
            consumer.accept(data);
        }
    }

    public static void updateStringLambda(String data, Consumer<String> consumer) {
        if (data != null && !data.isBlank()) {
            consumer.accept(data);
        }
    }
}
