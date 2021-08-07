package com.store.storeapp.product.entity;

import com.store.storeapp.Schema;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "product", schema = Schema.Inventory)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_description")
    private String productDesc;

    @Column(name = "product_selling_price")
    private BigDecimal productSellingPrice;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdOn;

    @Column(name = "modified_on")
    private Date modifiedOn;

    @Column(name = "product_unit")
    private String productUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public BigDecimal getProductSellingPrice() {
        return productSellingPrice;
    }

    public void setProductSellingPrice(BigDecimal productSellingPrice) {
        this.productSellingPrice = productSellingPrice;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }
}
