package com.store.storeapp.productwarehouse.repository;

import com.store.storeapp.productwarehouse.entity.ProductWarehouse;

import java.util.Collection;
import java.util.List;

public interface ProductWarehouseRepositoryCustom {
    List<ProductWarehouse> findAllByProdIds(Collection<Long> prodids);
}
