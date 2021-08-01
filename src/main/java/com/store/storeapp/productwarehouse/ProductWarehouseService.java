package com.store.storeapp.productwarehouse;

import com.store.storeapp.dto.ProductDto;
import com.store.storeapp.dto.ProductWarehouseDto;
import com.store.storeapp.dto.WarehouseDto;
import com.store.storeapp.product.ProductService;
import com.store.storeapp.product.entity.Product;
import com.store.storeapp.productwarehouse.entity.ProductWarehouse;
import com.store.storeapp.productwarehouse.repository.ProductWarehouseRepository;
import com.store.storeapp.warehouse.WarehouseService;
import com.store.storeapp.warehouse.entity.Warehouse;
import org.springframework.beans.BeanUtils;
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
    public void addStockToWareHouse(ProductWarehouseDto productWarehouseDto) {
        Long prodId = productWarehouseDto.getSelfId();
        List<ProductWarehouse> productWarehouseList = new ArrayList<>();
        for(WarehouseDto warehouseDto:productWarehouseDto.getWarehouseDtoList()){
            ProductWarehouse productWarehouse = new ProductWarehouse();
            productWarehouse.setWarehouseId(warehouseDto.getSelfId());
            productWarehouse.setProdId(prodId);
            productWarehouse.setWarehouseStock(warehouseDto.getWarehouseStock());
        }
        productWarehouseRepository.saveAll(productWarehouseList);
    }

    public ProductWarehouseDto getWarehouseProductDetail(Long prodid) {
        List<ProductWarehouse> productWarehouseList = productWarehouseRepository.findAllByProdId(prodid);
        ProductDto productDto = productService.getProductDetail(prodid);
        Set<Long> warehouseIds = productWarehouseList
                .stream()
                .map(ProductWarehouse::getWarehouseId)
                .collect(Collectors.toSet());
        Map<Long, BigDecimal> warehouseStockMap = productWarehouseList
                .stream()
                .collect(Collectors.toMap(ProductWarehouse::getWarehouseId,ProductWarehouse::getWarehouseStock));

        List<Warehouse> warehouses = warehouseService.getAllWarehouseByIds(warehouseIds);
        return populateProductWarehouseDto(productDto,warehouses,warehouseStockMap);
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
}
