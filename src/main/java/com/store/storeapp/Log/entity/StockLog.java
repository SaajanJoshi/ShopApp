package com.store.storeapp.Log.entity;

import com.store.storeapp.Schema;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stock_log",schema = Schema.Inventory)
public class StockLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "prod_id")
    private Long prodId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "log_detail")
    private String logDetail;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_on")
    private Date createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLogDetail() {
        return logDetail;
    }

    public void setLogDetail(String logDetail) {
        this.logDetail = logDetail;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
