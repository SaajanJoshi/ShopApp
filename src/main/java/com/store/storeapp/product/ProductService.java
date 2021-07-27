package com.store.storeapp.product;

import com.store.storeapp.Dto.ProductDto;
import com.store.storeapp.product.entity.Product;
import com.store.storeapp.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product getProductDetail(Long prodId){
        Optional<Product> product = productRepository.findById(prodId);
        return product.get();
    }

    public void addProduct(ProductDto productDto) {
        Product product = mapProductDtoToEntity(productDto);
        productRepository.save(product);
    }

    private Product mapProductDtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setProductCode(productDto.getProductCode());
        product.setProductDesc(productDto.getProductDescription());
        return product;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return mapProductsToProductDto(productList);
    }

    private List<ProductDto> mapProductsToProductDto(Collection<Product> productList) {
        List<ProductDto> productDtos = new ArrayList<>();

        for(Product product:productList){
            ProductDto productDto = new ProductDto();
            productDto.setProductCode(product.getProductCode());
            productDto.setProductDescription(product.getProductDesc());
            productDto.setSelfId(product.getId());
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public void deleteProduct(Long prodId) {
        Optional<Product> product = productRepository.findById(prodId);
        productRepository.delete(product.get());
    }
}
