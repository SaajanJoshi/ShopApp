package com.store.storeapp.Log.repository;

import com.store.storeapp.Log.entity.QStockLog;
import com.store.storeapp.QueryBuild;

public class StockLogRepositoryImpl extends QueryBuild implements StockLogRepositoryCustom {
    QStockLog qStockLog = QStockLog.stockLog;
}
