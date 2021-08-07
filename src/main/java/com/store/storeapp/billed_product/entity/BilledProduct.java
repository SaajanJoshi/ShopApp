package com.store.storeapp.billed_product.entity;

import com.store.storeapp.Schema;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "billed_product",schema = Schema.Report)
public class BilledProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "billing_report_id")
    private Long billingReportId;

    @Column(name = "prod_id")
    private Long prodId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "unit")
    private String unit;

    @Column(name = "quantity")
    private BigDecimal quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillingReportId() {
        return billingReportId;
    }

    public void setBillingReportId(Long billingReportId) {
        this.billingReportId = billingReportId;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
