package com.minicafe;

import com.minicafe.service.Cafe;

public class ApplicationRunner {
    public static void main(String[] args){
        new Cafe(10, 5).start();
    }
}
