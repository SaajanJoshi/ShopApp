package com.store.storeapp.warehouse.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.store.storeapp.QueryBuild;
import com.store.storeapp.warehouse.entity.QWarehouse;
import com.store.storeapp.warehouse.entity.Warehouse;

import java.util.Collection;
import java.util.List;

public class WarehouseRepositoryImpl extends QueryBuild implements WarehouseRepositoryCustom{

    QWarehouse qWarehouse = QWarehouse.warehouse;

    @Override
    public List<Warehouse> getAllWarehouseByIds(Collection<Long> warehouseIds) {
        JPAQuery getAllWarehouseById = createQuery();

        /**create query*/
       getAllWarehouseById
                .select(qWarehouse)
                .where(qWarehouse.Id.in(warehouseIds));

       /**fetch record */
      return getAllWarehouseById.fetch();
    }
}
