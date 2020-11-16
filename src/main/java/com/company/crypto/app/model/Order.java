package com.company.crypto.app.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Order {
    private OrderType orderType;
    private String userId;
    private String coinType;
    //the price and quantity are defined as int (after multiplying with 100)
    private int quantity;
    private int pricePerCoin;
}
