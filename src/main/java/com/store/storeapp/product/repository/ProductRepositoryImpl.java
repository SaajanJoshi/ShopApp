package com.store.storeapp.product.repository;

import com.store.storeapp.QueryBuild;
import com.store.storeapp.product.entity.Product;
import com.store.storeapp.product.entity.QProduct;

import java.util.Collection;
import java.util.List;

public class ProductRepositoryImpl extends QueryBuild implements ProductRepositoryCustom {
    QProduct qProduct = QProduct.product;

    @Override
    public List<Product> getAllWithProdids(Collection<Long> prodIds) {
        return createQuery()
                .selectFrom(qProduct)
                .where(qProduct.id.in(prodIds))
                .fetch();
    }

    @Override
    public List<Product> getAllByProductCode(Collection<String> prodCode) {
        return createQuery()
                .selectFrom(qProduct)
                .where(qProduct.productCode.in(prodCode))
                .fetch();
    }

    ;

    @Override
    public Product findByProductCode(String productCode) {
        return createQuery()
                .select(qProduct)
                .from(qProduct)
                .where(qProduct.productCode.eq(productCode))
                .fetchOne();
    }
}
