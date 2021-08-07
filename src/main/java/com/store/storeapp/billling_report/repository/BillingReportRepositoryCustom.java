package com.store.storeapp.billling_report.repository;

import com.store.storeapp.billling_report.entity.BillingReport;

import java.util.Date;
import java.util.List;

public interface BillingReportRepositoryCustom {
    List<BillingReport> getReports(Date fromDate, Date toDate);
}
