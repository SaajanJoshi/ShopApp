package com.store.storeapp;

import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class QueryBuild {
    @PersistenceContext
    private EntityManager entityManager;

    protected JPAQuery createQuery(){
        return new JPAQuery(entityManager);
    }
}
