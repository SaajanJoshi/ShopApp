package com.store.storeapp.controller;

import com.store.storeapp.Api.Api;
import com.store.storeapp.dto.ProductWarehouseDto;
import com.store.storeapp.dto.WarehouseDto;
import com.store.storeapp.productwarehouse.ProductWarehouseService;
import com.store.storeapp.warehouse.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(Api.InventoryUrl)
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    ProductWarehouseService productWarehouseService;
    private Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    @GetMapping("/getWarehouse/{warehouseId}")
    public HttpEntity getWarehouseDetail(@PathVariable(value="warehouseId") Long warehouseId){
        return new ResponseEntity(warehouseService.getWarehouseDetail(warehouseId), HttpStatus.OK);
    }

    @PostMapping("/addWarehouse")
    public HttpEntity addWarehouse(@RequestBody WarehouseDto warehouseDTO){
        Long warehouseId = warehouseService.addWarehouse(warehouseDTO);
        return new ResponseEntity(warehouseId,HttpStatus.OK);
    }

    @GetMapping("/allWarehouses")
    public HttpEntity getAllWarehouse(){
        return new ResponseEntity(warehouseService.getAllWarehouse(),HttpStatus.OK);
    }

    @PutMapping("/deleteWarehouse/{warehouseId}")
    public HttpEntity deleteWarehouse(@PathVariable(value = "warehouseId") Long warehouseId){
        warehouseService.deleteWarehouse(warehouseId);
        return new ResponseEntity("",HttpStatus.OK);
    }

    @PostMapping("/addStockToWarehouse")
    public HttpEntity addStockToWarehouse(@RequestBody ProductWarehouseDto productWarehouseDto){
        productWarehouseService.addStockToWareHouse(productWarehouseDto);
        return new ResponseEntity("",HttpStatus.OK);
    }

    @GetMapping("/getWarehouseStockDetail/{prodId}")
    public HttpEntity getWarehouseStockDetail(@PathVariable(value = "prodId") Long prodid){
        List<WarehouseDto> warehouseDto = productWarehouseService.getWarehouseStockDetail(prodid);
        logger.info("warehouseIds response : {}",warehouseDto);

        return new ResponseEntity(warehouseDto,HttpStatus.OK);
    }

    @GetMapping("/getTotalStock/{prodId}")
    public HttpEntity getTotalStock(@PathVariable(value = "prodId") Long prodid){
        BigDecimal totalStock = productWarehouseService.getTotalStock(prodid);
        return new ResponseEntity(totalStock,HttpStatus.OK);
    }
}
