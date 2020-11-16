package com.company.crypto.app;

import com.company.crypto.app.model.Order;
import com.company.crypto.app.model.OrderType;

import java.util.Map;

/**
 * This interface represents the Live Order Board operations.
 */
public interface LiveOrderBoard {
    void addOrder(Order order);

    void cancelOrder(Order order);

    Map<Integer, Integer> viewLiveOrders(OrderType orderType);
}
