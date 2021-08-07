package com.store.storeapp.product.repository;

import com.store.storeapp.QueryBuild;
import com.store.storeapp.product.entity.Product;
import com.store.storeapp.product.entity.QProduct;

import java.util.Collection;
import java.util.List;

public class ProductRepositoryImpl extends QueryBuild implements  ProductRepositoryCustom {
    QProduct qProduct = QProduct.product;

    @Override
    public List<Product> getAllWithProdids(Collection<Long> prodIds){
        return createQuery()
                .selectFrom(qProduct)
                .where(qProduct.id.in(prodIds))
                .fetch();
    }
}
