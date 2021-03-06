package com.company.crypto.app;

import com.company.crypto.app.model.Order;
import com.company.crypto.app.model.OrderType;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

/**
 * In-memory implementation of the LiverOrderBoard interface.
 */
public class InMemoryLiveOrderBoardImpl implements LiveOrderBoard {
    private static final int LIVE_ORDERS_MAX_SIZE = 10;
    private final List<Order> orders = new ArrayList<>();
    private static final Comparator<Map.Entry<Integer, Integer>> sellComparator = Map.Entry.comparingByKey();
    private static final Comparator<Map.Entry<Integer, Integer>> buyComparator = sellComparator.reversed();

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void cancelOrder(Order order) {
        orders.remove(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orders;
    }

    @Override
    public Map<Integer, Integer> viewLiveOrders(OrderType orderType) {

        Map<Integer, Integer> unsorted = orders.stream().filter(o -> o.getOrderType().equals(orderType))
                .collect(groupingBy(Order::getPricePerCoin, summingInt(Order::getQuantity)));

        return unsorted.entrySet().stream()
                .sorted(getComparator(orderType))
                .limit(LIVE_ORDERS_MAX_SIZE)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private Comparator<Map.Entry<Integer, Integer>> getComparator(OrderType orderType) {
        Comparator<Map.Entry<Integer, Integer>> comparator;
        if (orderType.equals(OrderType.SELL)) {
            comparator = sellComparator;
        } else {
            comparator = buyComparator;
        }
        return comparator;
    }

}
