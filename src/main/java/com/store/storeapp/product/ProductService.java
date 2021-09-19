package com.store.storeapp.product;

import com.store.storeapp.Excel.ExcelReaderUtil;
import com.store.storeapp.dto.ExcelDto;
import com.store.storeapp.dto.ProductDto;
import com.store.storeapp.dto.ProductWarehouseDto;
import com.store.storeapp.dto.WarehouseDto;
import com.store.storeapp.product.entity.Product;
import com.store.storeapp.product.repository.ProductRepository;
import com.store.storeapp.productwarehouse.ProductWarehouseService;
import com.store.storeapp.warehouse.WarehouseService;
import com.store.storeapp.warehouse.entity.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductWarehouseService productWarehouseService;
    @Autowired
    ExcelReaderUtil excelReaderUtil;
    @Autowired
    WarehouseService warehouseService;

    private Logger logger = LoggerFactory.getLogger(ProductService.class);
    public ProductDto getProductDetail(Long prodId){
        Optional<Product> product = productRepository.findById(prodId);
        return copyProductEntityToDto(product);
    }

    public ProductDto copyProductEntityToDto(Optional<Product> product){
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product.get(),productDto);
        return productDto;
    }
    public Long addProduct(ProductDto productDto) {
        Product product = mapProductDtoToEntity(productDto);
        return productRepository.save(product).getId();
    }

    private Product mapProductDtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setProductCode(productDto.getProductCode());
        product.setProductDesc(productDto.getProductDescription());
        product.setProductSellingPrice(productDto.getProductSellingPrice());
        return product;
    }

    public List<ProductDto> getAllProduct() {
        List<Product> productList = productRepository.findAll();
        return mapProductsToProductDto(productList);
    }

    private List<ProductDto> mapProductsToProductDto(Collection<Product> productList) {
        List<ProductDto> productDtos = new ArrayList<>();

        List<Long> prodIds = productList
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        Map<Long, BigDecimal> productToStockMap = productWarehouseService.getProductTotalStock(prodIds);

        for(Product product:productList){
            ProductDto productDto = new ProductDto();
            productDto.setProductCode(product.getProductCode());
            productDto.setProductDescription(product.getProductDesc());
            productDto.setSelfId(product.getId());
            productDto.setProductSellingPrice(product.getProductSellingPrice());
            productDto.setTotalStock(productToStockMap.get(product.getId()));
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public void deleteProduct(Long prodId) {
        Optional<Product> product = productRepository.findById(prodId);
        productRepository.delete(product.get());
    }

    public List<Product> getAllWithProdids(Collection<Long> prodIds){
        return productRepository.getAllWithProdids(prodIds);
    }

    public void readCsvAndStore(InputStream inputStream) throws IOException {
        excelReaderUtil.extractExcelContent(inputStream);
    }

    public void processProducts(List<ExcelDto> excelDtos) {
        prepareProductDto(excelDtos);

    }

    private void prepareProductDto(List<ExcelDto> excelDtos) {
        Set<String> itemNames = excelDtos.stream().map(ExcelDto::getItemName).collect(Collectors.toSet());
        Set<String> wareHouseCodes = excelDtos.stream().map(ExcelDto::getLocation).collect(Collectors.toSet());
        List<Product> productList = productRepository.getAllByProductCode(itemNames);
        List<Warehouse> warehouseList = warehouseService.getAllWarehouseByCode(wareHouseCodes);
        Map<String,Long> productToIdMap = productList.stream().collect(Collectors.toMap(Product::getProductCode, Product::getId));
        Map<String,Long> warehouseToIdMap = warehouseList.stream().collect(Collectors.toMap(Warehouse::getWarehouseCode, Warehouse::getId));
        Map<String,List<ExcelDto>> productToList = excelDtos.stream().collect(Collectors.groupingBy(ExcelDto::getItemName));

        for(String itemName : itemNames){
            Long itemId = getItemId(itemName,productToIdMap);
            List<ExcelDto> excelDtoList = productToList.get(itemName);

            ProductDto productDto = new ProductDto();
            productDto.setSelfId(itemId);
            productDto.setProductCode(itemName);
            BigDecimal sellingPrice = BigDecimal.valueOf(Double.valueOf(excelDtoList.get(0).getPrice()));
            productDto.setProductSellingPrice(sellingPrice);
            List<WarehouseDto> warehouseDtoList = new ArrayList<>();
            List<String> warehouseToCheck = new ArrayList<>();
            for(ExcelDto excelDto:excelDtoList){
                String warehouseCode = excelDto.getLocation();
                warehouseToCheck.add(warehouseCode);
                BigDecimal totalStock = BigDecimal.ZERO;
                totalStock = totalStock.add(BigDecimal.valueOf(Double.valueOf(excelDto.getOpeningQty())))
                        .add(BigDecimal.valueOf(Double.valueOf(excelDto.getPurchaseQty())));
                WarehouseDto warehouseDto = new WarehouseDto();
                Long warehouseid = getWarehouseId(warehouseCode,warehouseToIdMap);
                warehouseDto.setSelfId(warehouseid);
                warehouseDto.setWarehouseCode(warehouseCode);
                warehouseDto.setWarehouseStock(totalStock);
                warehouseDtoList.add(warehouseDto);
            }

            ProductWarehouseDto productWarehouseDto = new ProductWarehouseDto();
            BeanUtils.copyProperties(productDto,productWarehouseDto);
            productWarehouseDto.setWarehouseDtoList(warehouseDtoList);
            productWarehouseService.addStockToWareHouse(productWarehouseDto);
            if (!productToIdMap.containsKey(itemName)){
                Product product = productRepository.findByProductCode(itemName);
                if(product!=null){
                    productToIdMap.put(itemName,product.getId());
                }
            }

            logger.info("Locations : {}",warehouseToCheck);
            for(String locationName:warehouseToCheck){
                if (!warehouseToIdMap.containsKey(locationName)){
                    Warehouse warehouse = warehouseService.getWarehouseByCode(locationName);
                    if(warehouse!=null){
                        warehouseToIdMap.put(locationName,warehouse.getId());
                    }
                }
            }
        }

        /**minus stock*/
    }

    private Long getWarehouseId(String warehouseCode, Map<String, Long> warehouseToIdMap) {
        Long warehouseId = warehouseToIdMap.get(warehouseCode);
        if(warehouseId==null){
            /**product not available*/
            return null;
        }
        return warehouseId;
    }

    private Long getItemId(String itemName, Map<String, Long> productToIdMap) {
        Long itemId = productToIdMap.get(itemName);
        if(itemId==null){
            /**product not available*/
            return  null;
        }
        return itemId;
    }
}
