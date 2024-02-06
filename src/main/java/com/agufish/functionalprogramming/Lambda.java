package com.agufish.functionalprogramming;

import java.util.function.Function;

public class Lambda {
    public static void main(String[] args) {
        // 方法引用
        Integer integer = typeConverter(Integer::valueOf);
//        等价于 typeConverter((str) -> Integer.valueOf(str));
        System.out.println(integer);

    }

    public static <R> R typeConverter(Function<String, R> function) {
        String str = "123456";
        R result = function.apply(str);
        return result;
    }
}
