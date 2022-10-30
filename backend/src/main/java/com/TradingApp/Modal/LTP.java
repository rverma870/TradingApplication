package com.TradingApp.Modal;

import org.springframework.stereotype.Component;

import com.TradingApp.DTO.StockDetailsDTO;
import com.TradingApp.Enums.IdTypes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public final class LTP {

    private Double ltp;
    private Double tv;
    private Integer id;
    private static LTP ltpXYZ = new LTP();
    private static LTP ltpABC = new LTP();

    private LTP() {

    }

    public static void setStock(Double ltp, Double tv, Integer id, LTP obj) {
        obj.id = id;
        obj.tv = tv;
        obj.ltp = ltp;
    }

    public static LTP getStockXYZ() {
        return ltpXYZ;
    }

    public static LTP getStockABC() {
        return ltpABC;
    }

    public static StockDetailsDTO getCurrentLTP(Integer securityID) {

        Double ltp = 0.0;
        Double tv = 0.0;

        Integer ABC_ID = IdTypes.SECURITY_ABC.getIdValue();
        Integer XYZ_ID = IdTypes.SECURITY_XYZ.getIdValue();

        if (securityID.equals(ABC_ID)) {
            ltp = LTP.getStockABC().getLtp();
            tv = LTP.getStockABC().getTv();
        }

        if (securityID.equals(XYZ_ID)) {
            ltp = LTP.getStockXYZ().getLtp();
            tv = LTP.getStockXYZ().getTv();
        }

        return new StockDetailsDTO(ltp, tv);
    }
}
