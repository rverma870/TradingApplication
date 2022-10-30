package com.TradingApp.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketChangeDTO {
    private double id;
    private Object marketDefinition;
    private List<Object> rc;
    private boolean con;
    private boolean img;
}
