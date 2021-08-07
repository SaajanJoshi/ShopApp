package com.store.storeapp.billling_report.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.store.storeapp.QueryBuild;
import com.store.storeapp.billling_report.entity.BillingReport;
import com.store.storeapp.billling_report.entity.QBillingReport;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Date;
import java.util.List;

public class BillingReportRepositoryImpl extends QueryBuild implements BillingReportRepositoryCustom {
    QBillingReport qBillingReport = QBillingReport.billingReport;

    @Override
    public List<BillingReport> getReports(Date fromDate, Date toDate) {
        BooleanExpression combineQuery = createQueryFromDate(fromDate);
        combineQuery = combineQuery.and(createQueryToDate(toDate));

        JPAQueryFactory q = createQuery();
        return q.selectFrom(qBillingReport).where(combineQuery).fetch();
    }

    private BooleanExpression createQueryFromDate(Date fromDate) {
        return qBillingReport.createdOn.goe(fromDate);
    }

    private BooleanExpression createQueryToDate(Date ToDate) {
        return qBillingReport.createdOn.loe(ToDate);
    }
}
