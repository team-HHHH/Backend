package com.hhhh.dodream.global.common.utils;


import java.util.function.Consumer;

public class LambdaUtils {

    public static <T> void updateFieldUsingLambda(T data, Consumer<T> consumer) {
        if (data != null) {
            consumer.accept(data);
        }
    }

    public static void updateStringFieldUsingLambda(String data, Consumer<String> consumer) {
        if (data != null && !data.isBlank()) {
            consumer.accept(data);
        }
    }
}
