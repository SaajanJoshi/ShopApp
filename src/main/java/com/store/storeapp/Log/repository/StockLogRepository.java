package com.store.storeapp.Log.repository;

import com.store.storeapp.Log.entity.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockLogRepository extends JpaRepository<StockLog,Long>,StockLogRepositoryCustom {
}
