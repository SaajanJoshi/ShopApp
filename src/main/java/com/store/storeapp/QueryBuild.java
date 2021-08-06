package com.store.storeapp;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class QueryBuild {
    @PersistenceContext
    private EntityManager entityManager;

    protected JPAQueryFactory createQuery() {
        return new JPAQueryFactory(entityManager);
    }
}
