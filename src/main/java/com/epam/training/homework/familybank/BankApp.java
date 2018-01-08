package com.epam.training.homework.familybank;

import com.epam.training.homework.familybank.service.AccountOverviewService;
import com.epam.training.homework.familybank.service.TransactionService;
import com.epam.training.homework.familybank.spring.SpringConfigurationDao;
import com.epam.training.homework.familybank.spring.SpringConfigurationJpa;
import com.epam.training.homework.familybank.spring.SpringConfigurationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.math.BigDecimal;

public class BankApp {

    private final AccountOverviewService accountOverviewService;
    private final TransactionService transactionService;

    public BankApp() {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(
            SpringConfigurationDao.class,
            SpringConfigurationJpa.class,
            SpringConfigurationService.class);
        accountOverviewService = context.getBean(AccountOverviewService.class);
        transactionService = context.getBean(TransactionService.class);
    }

    public static void main(String[] args) {
        BankApp demo = new BankApp();
        demo.simulate();
    }

    private void simulate() {
        System.out.printf("The balance of Alan: %.2f%n", accountOverviewService.getBalanceByName("Alan"));
        System.out.printf("The balance of Ben: %.2f%n", accountOverviewService.getBalanceByName("Ben"));
        System.out.printf("The balance of Cecil: %.2f%n", accountOverviewService.getBalanceByName("Cecil"));
        System.out.printf("The balance of the bank: %.2f%n", accountOverviewService.getBankBalance());
        System.out.println();

        System.out.println("Alan and Cecil lend 1000 HUF to the bank...");
        transactionService.lend("Alan", new BigDecimal(1000));
        transactionService.lend("Cecil", new BigDecimal(1000));
        System.out.println("Ben withdraws all his money, and borrows 1000 HUF");
        transactionService.withdraw("Ben", new BigDecimal(2000));
        transactionService.borrow("Ben", new BigDecimal(1000));
        System.out.println("Cecil withdraws 1000 HUF to give it in cash to Ben...");
        transactionService.withdraw("Cecil", new BigDecimal(1000));
        System.out.println();

        System.out.printf("The balance of Alan: %.2f%n", accountOverviewService.getBalanceByName("Alan"));
        System.out.printf("The balance of Ben: %.2f%n", accountOverviewService.getBalanceByName("Ben"));
        System.out.printf("The balance of Cecil: %.2f%n", accountOverviewService.getBalanceByName("Cecil"));
        System.out.printf("The balance of the bank: %.2f%n", accountOverviewService.getBankBalance());
        System.out.println();

        for (int i = 0; i < 3; i++) {
            System.out.println("Calculating the interests...");
            transactionService.calculateInterests();
            System.out.println();

            System.out.printf("The balance of Alan: %.2f%n", accountOverviewService.getBalanceByName("Alan"));
            System.out.printf("The balance of Ben: %.2f%n", accountOverviewService.getBalanceByName("Ben"));
            System.out.printf("The balance of Cecil: %.2f%n", accountOverviewService.getBalanceByName("Cecil"));
            System.out.printf("The balance of the bank: %.2f%n", accountOverviewService.getBankBalance());
            System.out.println();
        }

    }
}
