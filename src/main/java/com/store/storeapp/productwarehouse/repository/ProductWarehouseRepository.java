package com.store.storeapp.productwarehouse.repository;

import com.store.storeapp.productwarehouse.entity.ProductWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse,Long>,ProductWarehouseRepositoryCustom {
    List<ProductWarehouse> findAllByProdId(Long prodId);
}
