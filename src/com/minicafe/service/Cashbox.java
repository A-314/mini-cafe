package com.minicafe.service;

import com.minicafe.model.Order;
import com.minicafe.util.CafeConst;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Cashbox implements Runnable{

    private static int idGenerator = 1;
    private final List<Order> orders =new ArrayList<>();
    private final BlockingQueue<Order> allOrders;

    public Cashbox(BlockingQueue<Order> allOrders) {
        this.allOrders = allOrders;
    }

    @Override
    public void run() {
        while(true){
            try{
                var order = allOrders.take();
                Thread.sleep(order.products().size() * CafeConst.PRODUCT_TIME_COST * 1000l);
                orders.add(order);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static int getId() {
        return idGenerator;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
