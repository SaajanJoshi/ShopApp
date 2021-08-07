package com.store.storeapp.product.repository;

import com.store.storeapp.product.entity.Product;

import java.util.Collection;
import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getAllWithProdids(Collection<Long> prodIds);
}
