package com.epam.training.homework.familybank.service;

import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public class PopulateDatabaseService {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Resource
    private PlatformTransactionManager transactionManager;

    public void populateDatabase() {
        System.out.println("Populating database...");
    }

}
