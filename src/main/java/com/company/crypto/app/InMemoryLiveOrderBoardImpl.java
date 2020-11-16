package com.company.crypto.app;

import com.company.crypto.app.model.Order;
import com.company.crypto.app.model.OrderType;
import com.company.crypto.app.view.LiveOrder;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

/**
 * In-memory implementation of the LiverOrderBoard interface
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
                .map(o -> new LiveOrder(o.getPricePerCoin(), o.getQuantity()))
                .collect(groupingBy(LiveOrder::getPrice, summingInt(LiveOrder::getQuantity)));

        Map<Integer, Integer> sorted = unsorted.entrySet().stream()
                .sorted(getComparator(orderType))
                .limit(LIVE_ORDERS_MAX_SIZE)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(),
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return sorted;

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
