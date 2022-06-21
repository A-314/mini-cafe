package com.minicafe.mapper;

import com.minicafe.csv.CashboxRow;
import com.minicafe.model.Order;
import com.minicafe.model.Product;
import com.minicafe.service.Cashbox;

import java.util.List;

public class CashboxMapper implements Mapper<Cashbox, CashboxRow>{

    @Override
    public CashboxRow map(Cashbox cashbox){
        return new CashboxRow(
                cashbox.getId(),
                cashbox.getOrders().size(),
                getOrderPriceSum(cashbox.getOrders())
        );
    }

    private Integer getOrderPriceSum(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.products().stream())
                .mapToInt(Product::price)
                .sum();
    }
}
