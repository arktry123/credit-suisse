package com.company.crypto.app;

import com.company.crypto.app.model.Order;
import com.company.crypto.app.model.OrderType;

import java.util.Map;

/**
 * This interface represents the Live Order Board operations.
 */
public interface LiveOrderBoard {
    /**
     * used to add an order to the existing list of orders
     *
     * @param order
     */
    void addOrder(Order order);

    /**
     * Used to cancel an order from the existing list of orders.
     *
     * @param order
     */
    void cancelOrder(Order order);

    /**
     * returns the map of price and quantity (Map<Integer, Integer>) for a given ordertype
     * - max of 10 entried will be returned.
     * - The key of the returned Map represents the price as an int (for ex. £13.6 - will be 1360)
     * - The value of the returned Map represents quantity as an int (for ex. 350.1 - will be 35010)
     *
     * @param orderType either SELL or BUY
     * @return Map of price and quantity
     */
    Map<Integer, Integer> viewLiveOrders(OrderType orderType);
}
