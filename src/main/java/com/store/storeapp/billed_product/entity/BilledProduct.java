package com.store.storeapp.billed_product.entity;

import com.store.storeapp.Schema;

import javax.persistence.*;

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
}
