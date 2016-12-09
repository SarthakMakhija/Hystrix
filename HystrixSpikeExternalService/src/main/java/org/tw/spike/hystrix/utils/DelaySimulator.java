package org.tw.spike.hystrix.utils;

import java.util.concurrent.TimeUnit;

public class DelaySimulator {

    private DelaySimulator(){}

    public static void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
