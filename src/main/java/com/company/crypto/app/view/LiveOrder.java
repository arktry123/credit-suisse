package com.company.crypto.app.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LiveOrder {
    //the price and quantity are defined as int (after multiplying with 100)
    private int price;
    private int quantity;
}
