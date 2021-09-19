package com.store.storeapp.Log;

import com.store.storeapp.Log.entity.StockLog;
import com.store.storeapp.Log.repository.StockLogRepository;
import com.store.storeapp.product.ProductService;
import com.store.storeapp.warehouse.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StockLogService {
    @Autowired
    StockLogRepository stockLogRepository;
    @Autowired
    ProductService productService;
    @Autowired
    WarehouseService warehouseService;

    private Logger logger = LoggerFactory.getLogger(StockLogService.class);

    public void createStockLog(Long prodId, Long warehouseId, BigDecimal warehouseStock, Boolean isAvailable) {
        StockLog stockLog = new StockLog();
        stockLog.setProdId(prodId);
        stockLog.setWarehouseId(warehouseId);

        logger.info("stock create for product id : {}",prodId);

        String productName = productService.getProductDetail(prodId).getProductCode();
        String warehouseName = warehouseService.getWarehouseDetail(warehouseId).getWarehouseCode();
        String logDetail;
        if (isAvailable){
            logDetail = "the stock of " + warehouseStock + " for product : " + productName + " has been updated to warehouse : " + warehouseName;
        } else {
            logDetail = "the stock of " + warehouseStock + " for product : " + productName + " has been added to warehouse : " + warehouseName;
        }
        stockLog.setLogDetail(logDetail);
        stockLogRepository.save(stockLog);
    }
}
