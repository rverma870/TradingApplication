package com.TradingApp.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.TradingApp.DTO.MarketChangeDTO;
import com.TradingApp.DTO.SecurityDTO;
import com.TradingApp.DTO.StockPriceDTO;
import com.TradingApp.Enums.IdTypes;
import com.TradingApp.Modal.LTP;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataLoader {

    @Value("${dataSample.path}")
    private String dataSamplePath;

    @Autowired
    private SimpMessagingTemplate simpleMessagingTemplate;

    @Async
    public void publishLivePrices() {

        SecurityDTO security = null;
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<SecurityDTO> typeReference = new TypeReference<SecurityDTO>() {
        };
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(dataSamplePath));
            String data = reader.readLine();

            if (data != null)
                security = mapper.readValue(data, typeReference);

            Long prevTimePoint = 1554733813472L;

            while (security != null) {

                Long currTimePoint = security.getPt();
                try {
                    Thread.sleep(currTimePoint - prevTimePoint);
                    prevTimePoint = currTimePoint;

                    for (MarketChangeDTO mc : security.getMc()) {

                        if (mc.getRc().size() > 0) {

                            for (Object rc : mc.getRc()) {
                                LinkedHashMap<String, Object> obj = (LinkedHashMap<String, Object>) rc;

                                if (obj.get("atb") != null) {
                                    List<List<?>> price_volume_arr = ((List<List<?>>) obj.get("atb"));

                                    try {
                                        Double price;
                                        Double volume;
                                        Integer id = (Integer) obj.get("id");

                                        String priceObjClassName = price_volume_arr.get(0).get(0).getClass()
                                                .getSimpleName();
                                        String volumeObjClassName = price_volume_arr.get(0).get(1).getClass()
                                                .getSimpleName();

                                        if (priceObjClassName.equals("Integer")) {
                                            price = ((Integer) price_volume_arr.get(0).get(0)).doubleValue();
                                        } else {
                                            price = (Double) price_volume_arr.get(0).get(0);
                                        }

                                        if (volumeObjClassName.equals("Integer")) {
                                            volume = ((Integer) price_volume_arr.get(0).get(1)).doubleValue();
                                        } else {
                                            volume = (Double) price_volume_arr.get(0).get(1);
                                        }

                                        StockPriceDTO stockPrice = new StockPriceDTO(price, volume, id);
                                        simpleMessagingTemplate.convertAndSend("/topic/get-live-price", stockPrice);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                if (obj.get("ltp") != null) {

                                    try {
                                        Double lastTransactionPrice;
                                        Double totalVolume;
                                        Integer id = (Integer) obj.get("id");

                                        String ltpObjClassName = obj.get("ltp").getClass().getSimpleName();
                                        String tvObjClassName = obj.get("tv").getClass().getSimpleName();

                                        if (ltpObjClassName.equals("Integer")) {
                                            lastTransactionPrice = ((Integer) obj.get("ltp")).doubleValue();
                                        } else {
                                            lastTransactionPrice = (Double) obj.get("ltp");
                                        }

                                        if (tvObjClassName.equals("Integer")) {
                                            totalVolume = ((Integer) obj.get("tv")).doubleValue();
                                        } else {
                                            totalVolume = (Double) obj.get("tv");
                                        }

                                        LTP ltp;
                                        if (id.equals(IdTypes.SECURITY_XYZ.getIdValue()))
                                            ltp = LTP.getStockXYZ();
                                        else
                                            ltp = LTP.getStockABC();

                                        LTP.setStock(lastTransactionPrice, totalVolume, id, ltp);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                data = reader.readLine();
                if (data != null)
                    security = mapper.readValue(data, typeReference);
                else
                    security = null;
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
