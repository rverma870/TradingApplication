package com.TradingApp.Enums;

public enum OrderStatus {
    ORDER_SUCCESS("Order Success"), ORDER_FAILED("Order Failed"),
    SELL_SUCCESS("Sell Success"), SELL_FAILED("Sell Failed"),
    ERROR_SELL_VOLUME("You don't have Enough Stock to Sell! Available Stk_Quantity : ");

    private String message;

    public String getOrderStatus() {
        return this.message;
    }

    private OrderStatus(String message) {
        this.message = message;
    }
}
