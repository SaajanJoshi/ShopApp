package com.store.storeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WarehouseDto {
    private Long selfId;
    private String warehouseCode;
    private String warehouseDesc;
    private BigDecimal warehouseStock;
    private Date createdOn;
    private Date modifiedOn;
}
