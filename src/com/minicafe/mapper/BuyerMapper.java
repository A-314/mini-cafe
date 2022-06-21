package com.minicafe.mapper;

import com.minicafe.csv.BuyerRow;
import com.minicafe.model.Order;
import com.minicafe.model.Product;
import com.minicafe.service.Buyer;

import java.util.List;

public class BuyerMapper implements Mapper<Buyer, BuyerRow>{

    @Override
    public BuyerRow map(Buyer buyer) {
        return new BuyerRow(
                buyer.getId(),
                buyer.getOrders().size(),
                getCaloriesAvg(buyer.getOrders()),
                getOrderPriceAvg(buyer.getOrders())
        );
    }

    private Double getOrderPriceAvg(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.products().stream())
                .mapToInt(Product::calories)
                .average()
                .orElse(0.0);
    }

    private Double getCaloriesAvg(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.products().stream())
                .mapToInt(Product::calories)
                .average()
                .orElse(0.0);
    }
}
