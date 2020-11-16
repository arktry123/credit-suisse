package com.company.crypto.app;

import com.company.crypto.app.model.Order;
import com.company.crypto.app.model.OrderType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

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

    @Test
    void testOnlySellOrders() {
        orderBoard.addOrder(new Order(OrderType.SELL, "userid1", "cointype", 10, 700));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid2", "cointype", 11, 600));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid3", "cointype", 10, 500));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid4", "cointype", 10, 400));

        HashMap<Integer, Integer> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(400, 10);
        expectedOutput.put(500, 10);
        expectedOutput.put(600, 11);
        expectedOutput.put(700, 10);

        Assertions.assertThat(orderBoard.viewLiveOrders(OrderType.SELL)).isEqualTo(expectedOutput);
    }

    @Test
    void testOnlyBuyOrders() {
        orderBoard.addOrder(new Order(OrderType.BUY, "userid1", "cointype", 10, 700));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid2", "cointype", 11, 600));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid3", "cointype", 10, 500));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid4", "cointype", 10, 400));

        HashMap<Integer, Integer> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(700, 10);
        expectedOutput.put(600, 11);
        expectedOutput.put(500, 10);
        expectedOutput.put(400, 10);

        Assertions.assertThat(orderBoard.viewLiveOrders(OrderType.BUY)).isEqualTo(expectedOutput);
    }
    @Test
    void testSellWhenNoSellOrders() {
        orderBoard.addOrder(new Order(OrderType.BUY, "userid1", "cointype", 10, 700));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid2", "cointype", 11, 600));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid3", "cointype", 10, 500));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid4", "cointype", 10, 400));

        Assertions.assertThat(orderBoard.viewLiveOrders(OrderType.SELL)).isEqualTo(Collections.EMPTY_MAP);
    }
    @Test
    void testBuyWhenNoBuyOrders() {
        orderBoard.addOrder(new Order(OrderType.SELL, "userid1", "cointype", 10, 700));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid2", "cointype", 11, 600));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid3", "cointype", 10, 500));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid4", "cointype", 10, 400));

        Assertions.assertThat(orderBoard.viewLiveOrders(OrderType.BUY)).isEqualTo(Collections.EMPTY_MAP);
    }

    @Test
    void testBuyForMixedOrders() {
        orderBoard.addOrder(new Order(OrderType.BUY, "userid1", "cointype", 10, 700));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid2", "cointype", 11, 600));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid3", "cointype", 10, 500));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid4", "cointype", 10, 400));

        HashMap<Integer, Integer> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(700, 10);
        expectedOutput.put(600, 11);
        expectedOutput.put(500, 10);

        Assertions.assertThat(orderBoard.viewLiveOrders(OrderType.BUY)).isEqualTo(expectedOutput);
    }

    @Test
    void testSellForMixedOrders() {
        orderBoard.addOrder(new Order(OrderType.SELL, "userid1", "cointype", 10, 700));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid2", "cointype", 11, 600));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid3", "cointype", 10, 500));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid4", "cointype", 10, 400));

        HashMap<Integer, Integer> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(400, 10);
        expectedOutput.put(500, 10);
        expectedOutput.put(700, 10);

        Assertions.assertThat(orderBoard.viewLiveOrders(OrderType.SELL)).isEqualTo(expectedOutput);
    }

    @Test
    void testSellWhenSamePriceForDifferentOrders() {
        orderBoard.addOrder(new Order(OrderType.SELL, "userid1", "cointype", 10, 400));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid2", "cointype", 11, 600));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid3", "cointype", 10, 500));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid4", "cointype", 11, 400));

        HashMap<Integer, Integer> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(400, 21);
        expectedOutput.put(500, 10);

        Assertions.assertThat(orderBoard.viewLiveOrders(OrderType.SELL)).isEqualTo(expectedOutput);
    }

    @Test
    void testBuyWhenSamePriceForDifferentOrders() {
        orderBoard.addOrder(new Order(OrderType.BUY, "userid1", "cointype", 10, 400));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid2", "cointype", 11, 600));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid3", "cointype", 13, 600));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid4", "cointype", 11, 400));

        HashMap<Integer, Integer> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(600, 24);
        expectedOutput.put(400, 10);

        Assertions.assertThat(orderBoard.viewLiveOrders(OrderType.BUY)).isEqualTo(expectedOutput);
    }

    @Test
    void testBuyWhenCanceledOneOrder() {
        orderBoard.addOrder(new Order(OrderType.BUY, "userid1", "cointype", 10, 400));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid2", "cointype", 11, 600));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid3", "cointype", 13, 600));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid4", "cointype", 11, 400));
        orderBoard.cancelOrder(new Order(OrderType.BUY, "userid3", "cointype", 13, 600));


        HashMap<Integer, Integer> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(600, 11);
        expectedOutput.put(400, 10);

        Assertions.assertThat(orderBoard.viewLiveOrders(OrderType.BUY)).isEqualTo(expectedOutput);
    }

    @Test
    void testSellWhenCanceledOneOrder() {
        orderBoard.addOrder(new Order(OrderType.SELL, "userid1", "cointype", 10, 400));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid2", "cointype", 11, 600));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid3", "cointype", 13, 600));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid4", "cointype", 11, 400));
        orderBoard.cancelOrder(new Order(OrderType.SELL, "userid3", "cointype", 13, 600));


        HashMap<Integer, Integer> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(400, 21);

        Assertions.assertThat(orderBoard.viewLiveOrders(OrderType.SELL)).isEqualTo(expectedOutput);
    }

    @Test
    void testSellWhenCancellingNonExistingOneOrder() {
        orderBoard.addOrder(new Order(OrderType.SELL, "userid1", "cointype", 10, 400));
        orderBoard.addOrder(new Order(OrderType.BUY, "userid2", "cointype", 11, 600));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid3", "cointype", 13, 600));
        orderBoard.addOrder(new Order(OrderType.SELL, "userid4", "cointype", 11, 400));
        orderBoard.cancelOrder(new Order(OrderType.SELL, "userid4", "cointype", 13, 600));


        HashMap<Integer, Integer> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(400, 21);
        expectedOutput.put(600, 13);

        Assertions.assertThat(orderBoard.viewLiveOrders(OrderType.SELL)).isEqualTo(expectedOutput);
    }
}