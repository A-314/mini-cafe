package com.minicafe.util;

import java.util.Random;

public class RandomUtil {

    private static final Random RANDOM = new Random();

    private RandomUtil(){}

    public static int get(int max){
        return RANDOM.nextInt(max);
    }
}
