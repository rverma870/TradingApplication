package com.TradingApp.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.TradingApp.DTO.StockPriceDTO;
import com.TradingApp.service.SecurityService;

@RestController
public class SecurityController {

    @Autowired
    SecurityService securityService;

    @PostMapping(value = "/user/buy", consumes = "application/json")
    public CompletableFuture<ResponseEntity<String>> buySecurity(@RequestBody StockPriceDTO stockPrice) {

        CompletableFuture<String> response = securityService.buySecurity(stockPrice);
        try {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.CREATED).body(response.get()));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @PostMapping(value = "/user/sell", consumes = "application/json")
    public CompletableFuture<ResponseEntity<String>> sellSecurity(@RequestBody StockPriceDTO stockPrice) {

        CompletableFuture<String> response = securityService.sellSecurity(stockPrice);
        try {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).body(response.get()));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }
}
