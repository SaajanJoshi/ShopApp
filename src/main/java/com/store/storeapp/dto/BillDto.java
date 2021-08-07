package com.store.storeapp.dto;

import java.util.List;

public class BillDto extends BillReportDto {

    List<BillProductDto> productDtoList;

    public List<BillProductDto> getProductDtoList() {
        return productDtoList;
    }

    public void setProductDtoList(List<BillProductDto> productDtoList) {
        this.productDtoList = productDtoList;
    }
}
