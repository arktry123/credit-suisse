package com.company.crypto.app.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Order {
    private final OrderType orderType;
    private final String userId;
    private final String coinType;
    //the price and quantity are defined as int (after multiplying with 100)
    private final int quantity;
    private final int pricePerCoin;
}
