package com.company.crypto.app;

import com.company.crypto.app.model.Order;
import com.company.crypto.app.model.OrderType;
import com.company.crypto.app.view.LiveOrder;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class InMemoryLiveOrderBoardImpl implements LiveOrderBoard {
    private List<Order> orders = new ArrayList<>();

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void cancelOrder(Order order) {
        orders.remove(order);
    }

    @Override
    public Map<Integer, Integer> viewLiveOrders(OrderType orderType) {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return orders;
    }

}
