package com.store.storeapp.billling_report.repository;

import com.store.storeapp.billling_report.entity.BillingReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingReportRepository extends JpaRepository<BillingReport,Long>,BillingReportRepositoryCustom {
}
