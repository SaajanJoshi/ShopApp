package com.store.storeapp.warehouse.entity;

import com.store.storeapp.Schema;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "warehouse",schema = Schema.Inventory)
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "warehouse_code")
    private String warehouseCode;

    @Column(name = "warehouse_description")
    private String warehouseDesc;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "modified_on")
    private Date modifiedOn;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseDesc() {
        return warehouseDesc;
    }

    public void setWarehouseDesc(String warehouseDesc) {
        this.warehouseDesc = warehouseDesc;
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

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
}
