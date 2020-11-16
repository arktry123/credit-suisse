package com.company.crypto.app;

import com.company.crypto.app.model.Order;
import com.company.crypto.app.model.OrderType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class LiveOrderBoardTest {

    private LiveOrderBoard orderBoard = new InMemoryLiveOrderBoardImpl();

    @Test
    void testAdd() {
        orderBoard.addOrder(new Order(OrderType.SELL, "userid1", "cointype", 10, 700));
        Assertions.assertThat(orderBoard.getAllOrders()).hasSize(1);
    }

    @Test
    void testAddAndCancel() {
        orderBoard.addOrder(new Order(OrderType.SELL, "userid1", "cointype", 10, 700));
        orderBoard.cancelOrder(new Order(OrderType.SELL, "userid1", "cointype", 10, 700));
        Assertions.assertThat(orderBoard.getAllOrders()).hasSize(0);
    }

    @Test
    void testCancelNonExistingOrder() {
        orderBoard.addOrder(new Order(OrderType.SELL, "userid1", "cointype", 10, 700));
        orderBoard.cancelOrder(new Order(OrderType.SELL, "userid2", "cointype", 10, 700));
        Assertions.assertThat(orderBoard.getAllOrders()).hasSize(1);
    }
}
