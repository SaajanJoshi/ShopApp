package com.store.storeapp.billed_product.repository;

import com.store.storeapp.billed_product.entity.BilledProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BilledProductRepository extends JpaRepository<BilledProduct,Long>,BilledProductRepositoryCustom {
}
