package com.store.storeapp.Excel;

import com.store.storeapp.dto.ExcelDto;
import com.store.storeapp.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExcelReaderUtil {
    @Autowired
    FileParser fileParser;
    @Autowired
    ProductService productService;
    private Logger logger = LoggerFactory.getLogger(ExcelReaderUtil.class);

    public void extractExcelContent(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = "";
        Boolean skipInitialLine = Boolean.TRUE;
        List<ExcelDto> excelDtos = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (skipInitialLine) {
                skipInitialLine = Boolean.FALSE;
                continue;
            }

            List<String> records = Arrays.asList(line.split(Delimiter.COMMA_SEPERATED));
            logger.info("Records : {}", records);
            ExcelDto excelDto = fileParser.parse(records);
            excelDtos.add(excelDto);
        }
        productService.processProducts(excelDtos);
    }
}
