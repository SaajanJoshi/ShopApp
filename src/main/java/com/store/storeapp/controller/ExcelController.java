package com.store.storeapp.controller;

import com.store.storeapp.Api.Api;
import com.store.storeapp.Excel.ExcelReaderUtil;
import com.store.storeapp.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(Api.ExcelLoadUrl)
public class ExcelController {

    @Autowired
    ExcelReaderUtil excelReaderUtil;

    @Autowired
    ProductService productService;

    @PostMapping("/loadExcel")
    public HttpEntity loadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream =  new BufferedInputStream(file.getInputStream());

        productService.readCsvAndStore(inputStream);
        return new ResponseEntity("Success", HttpStatus.OK);
    }
}
