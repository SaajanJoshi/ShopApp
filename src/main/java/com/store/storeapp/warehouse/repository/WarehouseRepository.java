package com.store.storeapp.warehouse.repository;

import com.store.storeapp.warehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long>,WarehouseRepositoryCustom {
}
