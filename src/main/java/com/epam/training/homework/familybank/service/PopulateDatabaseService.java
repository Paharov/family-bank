package com.epam.training.homework.familybank.service;

import com.epam.training.homework.familybank.domain.Account;
import com.epam.training.homework.familybank.domain.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public class PopulateDatabaseService {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private User bankUser;
    @Resource
    private Account bank;

    public void populateDatabase() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        entityManager.persist(bankUser);
        entityManager.persist(bank);

        User alan = new User();
        alan.setName("Alan");
        entityManager.persist(alan);
        Account alanAccount = new Account();
        alanAccount.setBalance(new BigDecimal(2000));
        alanAccount.setUser(alan);
        entityManager.persist(alanAccount);

        User ben = new User();
        ben.setName("Ben");
        entityManager.persist(ben);
        Account benAccount = new Account();
        benAccount.setBalance(new BigDecimal(2000));
        benAccount.setUser(ben);
        entityManager.persist(benAccount);

        User cecil = new User();
        cecil.setName("Cecil");
        entityManager.persist(cecil);
        Account cecilAccount = new Account();
        cecilAccount.setBalance(new BigDecimal(2000));
        cecilAccount.setUser(cecil);
        entityManager.persist(cecilAccount);

        transactionManager.commit(status);
    }

}
