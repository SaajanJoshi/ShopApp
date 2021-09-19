package com.store.storeapp.Excel;

import com.store.storeapp.dto.ExcelDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileParser {
    public ExcelDto parse(List<String> record) {
        ExcelDto excelDto = new ExcelDto();
        excelDto.setItemName(record.get(1));
        excelDto.setLocation(record.get(2));
        excelDto.setOpeningQty(record.get(3));
        excelDto.setPrice(record.get(4));
        excelDto.setSalesQty(record.get(5));
        excelDto.setPurchaseQty(record.get(6));
        return excelDto;
    }
}
