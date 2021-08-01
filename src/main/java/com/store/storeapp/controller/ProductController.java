package com.store.storeapp.controller;

import com.store.storeapp.Api.Api;
import com.store.storeapp.dto.ProductDto;
import com.store.storeapp.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Api.ProductUrl)
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/getProduct/{prodId}")
    public HttpEntity getProductDetail(@PathVariable(value="prodId") Long prodId){
        return new ResponseEntity(productService.getProductDetail(prodId), HttpStatus.OK);
    }

    @PostMapping("/addProduct")
    public HttpEntity addProduct(@RequestBody ProductDto productDto){
        productService.addProduct(productDto);
        return new ResponseEntity("",HttpStatus.OK);
    }

    @GetMapping("/allProducts")
    public HttpEntity getAllProduct(){
        return new ResponseEntity(productService.getAllProduct(),HttpStatus.OK);
    }

    @PutMapping("/deleteProduct/{prodId}")
    public HttpEntity deleteProduct(@PathVariable(value = "prodId") Long prodId){
        productService.deleteProduct(prodId);
        return new ResponseEntity("",HttpStatus.OK);
    }
}
