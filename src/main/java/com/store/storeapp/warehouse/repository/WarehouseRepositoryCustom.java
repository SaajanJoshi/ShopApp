package com.store.storeapp.warehouse.repository;

import com.store.storeapp.warehouse.entity.Warehouse;

import java.util.Collection;
import java.util.List;

public interface WarehouseRepositoryCustom {
    List<Warehouse> getAllWarehouseByIds(Collection<Long> warehouseIds);
}
