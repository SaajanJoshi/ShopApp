package com.store.storeapp.productwarehouse.entity;

import com.store.storeapp.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_warehouse",schema = Schema.Inventory)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductWarehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @Column(name = "prod_id")
    private Long prodId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "warehouse_stock")
    private BigDecimal warehouseStock;
}
