package com.TradingApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPriceDTO {
    private Double price;
    private Double volume;
    private Integer id;
}
