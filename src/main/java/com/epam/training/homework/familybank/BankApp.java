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

    private static final BigDecimal LENDING_RATE = new BigDecimal(0.001);
    private static final BigDecimal BORROWING_RATE = new BigDecimal(0.002);

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
        System.out.printf("The debts of Alan: %.2f%n", accountOverviewService.getDebtsByName("Alan"));
        System.out.printf("The assets of Alan: %.2f%n", accountOverviewService.getAssetsByName("Alan"));
        System.out.println();

        System.out.printf("The balance of Ben: %.2f%n", accountOverviewService.getBalanceByName("Ben"));
        System.out.printf("The debts of Ben: %.2f%n", accountOverviewService.getDebtsByName("Ben"));
        System.out.printf("The assets of Ben: %.2f%n", accountOverviewService.getAssetsByName("Ben"));
        System.out.println();

        System.out.printf("The balance of Cecil: %.2f%n", accountOverviewService.getBalanceByName("Cecil"));
        System.out.printf("The debts of Cecil: %.2f%n", accountOverviewService.getDebtsByName("Cecil"));
        System.out.printf("The assets of Cecil: %.2f%n", accountOverviewService.getAssetsByName("Cecil"));
        System.out.println();

        System.out.println("Cecil sends 1000 HUF as a gift to Ben...");
        transactionService.gift("Cecil", "Ben", new BigDecimal(1000));
        System.out.printf("The balance of Cecil after gifting 1000 HUF: %.2f%n",
                          accountOverviewService.getBalanceByName("Cecil"));
        System.out.printf("The balance of Ben after receiving 1000 HUF: %.2f%n",
                          accountOverviewService.getBalanceByName("Ben"));

    }
}
