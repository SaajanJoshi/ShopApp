package com.store.storeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelDto {
    private String itemName;
    private String location;
    private String openingQty;
    private String price;
    private String salesQty;
    private String purchaseQty;
}
