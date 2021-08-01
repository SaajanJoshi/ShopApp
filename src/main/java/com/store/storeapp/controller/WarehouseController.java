package com.store.storeapp.controller;

import com.store.storeapp.Api.Api;
import com.store.storeapp.dto.ProductWarehouseDto;
import com.store.storeapp.dto.WarehouseDto;
import com.store.storeapp.productwarehouse.ProductWarehouseService;
import com.store.storeapp.warehouse.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Api.InventoryUrl)
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    ProductWarehouseService productWarehouseService;

    @GetMapping("/getWarehouse/{warehouseId}")
    public HttpEntity getWarehouseDetail(@PathVariable(value="warehouseId") Long warehouseId){
        return new ResponseEntity(warehouseService.getWarehouseDetail(warehouseId), HttpStatus.OK);
    }

    @PostMapping("/addWarehouse")
    public HttpEntity addWarehouse(@RequestBody WarehouseDto warehouseDTO){
        warehouseService.addWarehouse(warehouseDTO);
        return new ResponseEntity("",HttpStatus.OK);
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
    public HttpEntity addStockToWarehouse(ProductWarehouseDto productWarehouseDto){
        productWarehouseService.addStockToWareHouse(productWarehouseDto);
        return new ResponseEntity("",HttpStatus.OK);
    }

    @GetMapping("/getWarehouseProductDetail/{prodId}")
    public HttpEntity getWarehouseProductDetail(@PathVariable(value = "prodId") Long prodid){
        ProductWarehouseDto productWarehouseDto = productWarehouseService.getWarehouseProductDetail(prodid);
        return new ResponseEntity("",HttpStatus.OK);
    }
}
