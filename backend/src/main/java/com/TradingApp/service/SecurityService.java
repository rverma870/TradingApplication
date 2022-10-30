package com.TradingApp.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.TradingApp.DTO.StockDetailsDTO;
import com.TradingApp.DTO.StockPriceDTO;
import com.TradingApp.Enums.OrderStatus;
import com.TradingApp.Enums.StockOperation;
import com.TradingApp.Modal.LTP;
import com.TradingApp.Modal.User;

@Service
public class SecurityService {

    public Logger logger = LoggerFactory.getLogger(SecurityService.class);

    @Async
    public CompletableFuture<String> buySecurity(StockPriceDTO buyStockPrice) {

        Integer securityId = buyStockPrice.getId();
        Double securityPrice = buyStockPrice.getPrice();
        Double securityVolume = buyStockPrice.getVolume();

        if (securityVolume == null || securityVolume <= 0) {
            return CompletableFuture.completedFuture("Enter Valid Number Units To Buy");
        }

        StockDetailsDTO currStockLTP = LTP.getCurrentLTP(securityId);

        Double ltp = currStockLTP.getPrice();
        Double tv = currStockLTP.getVolume();

        if (securityPrice.equals(ltp) && securityVolume.doubleValue() <= tv.doubleValue()) {

            User.updateUserPortfolio(securityId, securityVolume, StockOperation.BUY_STOCK);

            logger.info("STK_ID : " + securityId + " BUY_PRICE : " + securityPrice + " LTP : " + ltp + "BUY_VOL"
                    + securityVolume
                    + " USR_SEC_QTY : " + User.getUserSecurityQuantity(securityId)
                    + " ORDER_STATUS : " + OrderStatus.ORDER_SUCCESS.getOrderStatus());

            return CompletableFuture.completedFuture(OrderStatus.ORDER_SUCCESS.getOrderStatus());
        }

        logger.info("STK_ID : " + securityId + " BUY_PRICE : " + securityPrice + " LTP : " + ltp + "BUY_VOL"
                + securityVolume
                + " USR_SEC_QTY: " + User.getUserSecurityQuantity(securityId)
                + " ORDER_STATUS : " + OrderStatus.ORDER_FAILED.getOrderStatus());

        return CompletableFuture.completedFuture(OrderStatus.ORDER_FAILED.getOrderStatus());
    }

    @Async
    public CompletableFuture<String> sellSecurity(StockPriceDTO sellStockPrice) {

        Integer securityId = sellStockPrice.getId();
        Double securityPrice = sellStockPrice.getPrice();
        Double securityVolume = sellStockPrice.getVolume();

        if (securityVolume == null || securityVolume <= 0) {
            return CompletableFuture.completedFuture("Enter Valid Number Units To Sell");
        }

        Double userSecurityVolume = User.getUserSecurityQuantity(securityId);

        if (securityVolume.doubleValue() <= userSecurityVolume.doubleValue()) {

            User.updateUserPortfolio(securityId, securityVolume, StockOperation.SELL_STOCK);

            logger.info("STK_ID : " + securityId + " SELL_PRICE : " + securityPrice + "BUY_VOL" + securityVolume +
                    " USR_SEC_QTY : " + User.getUserSecurityQuantity(securityId) +
                    " ORDER_STATUS : " + OrderStatus.SELL_SUCCESS.getOrderStatus());

            return CompletableFuture.completedFuture(OrderStatus.SELL_SUCCESS.getOrderStatus());
        }

        logger.info("STK_ID : " + securityId + " BUY_PRICE : " + securityPrice + "BUY_VOL" + securityVolume +
                " USR_SEC_QTY : " + User.getUserSecurityQuantity(securityId) +
                " ORDER_STATUS : " + OrderStatus.SELL_FAILED.getOrderStatus());

        return CompletableFuture.completedFuture(OrderStatus.ERROR_SELL_VOLUME.getOrderStatus() + userSecurityVolume);
    }
}
