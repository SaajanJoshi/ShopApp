package com.store.storeapp.product;

import com.store.storeapp.dto.ProductDto;
import com.store.storeapp.dto.ProductWarehouseDto;
import com.store.storeapp.product.entity.Product;
import com.store.storeapp.product.repository.ProductRepository;
import com.store.storeapp.productwarehouse.ProductWarehouseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductWarehouseService productWarehouseService;

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
}
