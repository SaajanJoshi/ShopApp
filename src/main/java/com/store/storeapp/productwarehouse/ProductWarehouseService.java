package com.store.storeapp.productwarehouse;

import com.store.storeapp.Log.StockLogService;
import com.store.storeapp.dto.ProductDto;
import com.store.storeapp.dto.ProductWarehouseDto;
import com.store.storeapp.dto.WarehouseDto;
import com.store.storeapp.product.ProductService;
import com.store.storeapp.productwarehouse.entity.ProductWarehouse;
import com.store.storeapp.productwarehouse.repository.ProductWarehouseRepository;
import com.store.storeapp.warehouse.WarehouseService;
import com.store.storeapp.warehouse.entity.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductWarehouseService {
    @Autowired
    ProductWarehouseRepository productWarehouseRepository;
    @Autowired
    ProductService productService;
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    StockLogService stockLogService;

    private Logger logger = LoggerFactory.getLogger(ProductWarehouseService.class);
    public void addStockToWareHouse(ProductWarehouseDto productWarehouseDto) {
        Long prodId = productWarehouseDto.getSelfId();
        if (prodId == null)
          prodId =  productService.addProduct(productWarehouseDto);

        List<ProductWarehouse> productWarehouses = productWarehouseRepository.findAllByProdId(prodId);

        List<ProductWarehouse> productWarehouseList = new ArrayList<>();
        for(WarehouseDto warehouseDto:productWarehouseDto.getWarehouseDtoList()){
            Boolean isAvailable = false;

            for(ProductWarehouse productWarehouse:productWarehouses){
                if(prodId.equals(productWarehouse.getProdId())&&warehouseDto.getSelfId().equals(productWarehouse.getWarehouseId())){
                    isAvailable = true;
                    productWarehouse.setId(productWarehouse.getId());
                }
            }

            createStockLog(prodId,warehouseDto.getSelfId(),warehouseDto.getWarehouseStock(),isAvailable);

            ProductWarehouse productWarehouse = new ProductWarehouse();
            productWarehouse.setWarehouseId(warehouseDto.getSelfId());
            productWarehouse.setProdId(prodId);
            productWarehouse.setWarehouseStock(warehouseDto.getWarehouseStock());
            productWarehouseList.add(productWarehouse);
        }
        productWarehouseRepository.saveAll(productWarehouseList);
    }

    private void createStockLog(Long prodId, Long warehouseId, BigDecimal warehouseStock, Boolean isAvailable) {
        stockLogService.createStockLog(prodId,warehouseId,warehouseStock,isAvailable);
    }

    public List<WarehouseDto> getWarehouseStockDetail(Long prodid) {
        List<ProductWarehouse> productWarehouseList = productWarehouseRepository.findAllByProdId(prodid);
        Set<Long> warehouseIds = productWarehouseList
                .stream()
                .map(ProductWarehouse::getWarehouseId)
                .collect(Collectors.toSet());

        Map<Long, BigDecimal> warehouseStockMap = productWarehouseList
                .stream()
                .collect(Collectors.toMap(ProductWarehouse::getWarehouseId,ProductWarehouse::getWarehouseStock));

        if(!warehouseIds.isEmpty()){
            List<Warehouse> warehouses = warehouseService.getAllWarehouseByIds(warehouseIds);
            return populateWarehouseDto(warehouses,warehouseStockMap);
        }
        return null;
    }

    private List<WarehouseDto> populateWarehouseDto(List<Warehouse> warehouses,Map<Long, BigDecimal> warehouseStockMap) {
        List<WarehouseDto> warehouseDtoList = new ArrayList<>();
        for (Warehouse warehouse:warehouses){
            WarehouseDto warehouseDto = new WarehouseDto();
            warehouseDto.setWarehouseDesc(warehouse.getWarehouseDesc());
            warehouseDto.setWarehouseStock(warehouseStockMap.get(warehouse.getId()));
            warehouseDto.setWarehouseCode(warehouse.getWarehouseCode());
            warehouseDto.setSelfId(warehouse.getId());
            warehouseDtoList.add(warehouseDto);
        }

        logger.info("warehouseDtoList : {}", warehouseDtoList);
        return warehouseDtoList;
    }

    private ProductWarehouseDto populateProductWarehouseDto(ProductDto productDto, List<Warehouse> warehouses,Map<Long, BigDecimal> warehouseStockMap) {
        ProductWarehouseDto productWarehouseDto = new ProductWarehouseDto();
        productWarehouseDto.setProductCode(productDto.getProductCode());
        productWarehouseDto.setProductDescription(productDto.getProductDescription());
        productWarehouseDto.setProductSellingPrice(productDto.getProductSellingPrice());
        productWarehouseDto.setSelfId(productDto.getSelfId());

        List<WarehouseDto> warehouseDtoList = new ArrayList<>();
        for (Warehouse warehouse:warehouses){
            WarehouseDto warehouseDto = new WarehouseDto();
            warehouseDto.setWarehouseDesc(warehouse.getWarehouseDesc());
            warehouseDto.setWarehouseStock(warehouseStockMap.get(warehouse.getId()));
            warehouseDto.setWarehouseCode(warehouse.getWarehouseCode());
            warehouseDto.setSelfId(warehouse.getId());
            warehouseDtoList.add(warehouseDto);
        }

        productWarehouseDto.setWarehouseDtoList(warehouseDtoList);
        return productWarehouseDto;
    }

    public BigDecimal getTotalStock(Long prodid) {
        List<ProductWarehouse> productWarehouseList = productWarehouseRepository.findAllByProdId(prodid);
        BigDecimal totalStock = BigDecimal.ZERO;

        for (ProductWarehouse productWarehouse : productWarehouseList) {
            totalStock.add(productWarehouse.getWarehouseStock());
        }
        return totalStock;
    }
}
