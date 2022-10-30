package com.TradingApp.Modal;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.TradingApp.Enums.StockOperation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public final class User {

    private HashMap<Integer, Double> portfolio = new HashMap<>();

    private static User user = new User();

    private User() {

    }

    public static User getUser() {
        return user;
    }

    public static Double getUserSecurityQuantity(Integer securityID) {
        return User.getUser().getPortfolio().getOrDefault(securityID, 0.0);
    }

    public static void updateUserPortfolio(Integer securityID, Double securityVolume, StockOperation operation) {

        Double availableUserSecurityQuantity = getUserSecurityQuantity(securityID);
        Double updatedUserSecurityQuantity = 0.0;

        if (operation.equals(StockOperation.BUY_STOCK))
            updatedUserSecurityQuantity = availableUserSecurityQuantity + securityVolume;
        else if (operation.equals(StockOperation.SELL_STOCK))
            updatedUserSecurityQuantity = availableUserSecurityQuantity - securityVolume;

        HashMap<Integer, Double> portfolio = user.portfolio;
        portfolio.put(securityID, updatedUserSecurityQuantity);
    }

}
