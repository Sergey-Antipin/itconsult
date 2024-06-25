package com.antipin.concurrency.concurrentbank;

import java.math.BigDecimal;

public class ConcurrentBankExample {

    public static void main(String[] args) {
        ConcurrentBank bank = new ConcurrentBank();

        // Создание счетов
        BankAccount account1 = bank.createAccount(BigDecimal.valueOf(2000.00));
        BankAccount account2 = bank.createAccount(BigDecimal.valueOf(1000.00));

        // Перевод между счетами
        //Thread transferThread1 = new Thread(() -> bank.transfer(account1, account2, BigDecimal.valueOf(200.00)));
        //Thread transferThread2 = new Thread(() -> bank.transfer(account2, account1, BigDecimal.valueOf(100.00)));

        for (int i =0; i < 1000; i++) {
            new Thread(() -> bank.transfer(account1, account2, BigDecimal.valueOf(2.00))).start();
            new Thread(() -> bank.transfer(account2, account1, BigDecimal.valueOf(1.00))).start();
            System.out.println(bank.getTotalBalance());
        }

        try {
            //transferThread1.join();
            //transferThread2.join();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вывод общего баланса
        System.out.println("Total balance: " + bank.getTotalBalance());
        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());
    }
}
