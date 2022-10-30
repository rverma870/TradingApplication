package com.TradingApp.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityDTO {
    private String op;
    private String clk;
    private Long pt;
    private List<MarketChangeDTO> mc;
}
