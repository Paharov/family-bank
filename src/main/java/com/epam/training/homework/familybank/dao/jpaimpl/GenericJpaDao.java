package com.epam.training.homework.familybank.dao.jpaimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GenericJpaDao {

    @PersistenceContext
    protected EntityManager entityManager;
}
