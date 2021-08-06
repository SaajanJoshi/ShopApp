package com.store.storeapp.productwarehouse.repository;

import com.store.storeapp.QueryBuild;
import com.store.storeapp.productwarehouse.entity.ProductWarehouse;
import com.store.storeapp.productwarehouse.entity.QProductWarehouse;

import java.util.Collection;
import java.util.List;

public class ProductWarehouseRepositoryImpl extends QueryBuild implements ProductWarehouseRepositoryCustom{

    QProductWarehouse productWarehouse = QProductWarehouse.productWarehouse;

    @Override
    public List<ProductWarehouse> findAllByProdIds(Collection<Long> prodids){
        return createQuery()
                .selectFrom(productWarehouse)
                .where(productWarehouse.prodId.in(prodids))
                .fetch();
    }
}
