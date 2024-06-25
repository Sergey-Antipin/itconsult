package com.antipin.concurrency.concurrentbank;

/*В виртуальном банке "ConcurrentBank" решено внедрить многопоточность для обработки операций
по счетам клиентов. Система должна поддерживать возможность одновременного пополнения (deposit),
снятия (withdraw), а также переводов (transfer) между счетами. Каждый счет имеет свой уникальный номер.

Реализуйте класс BankAccount с методами deposit, withdraw и getBalance, поддерживающими
многопоточное взаимодействие.

Реализуйте класс ConcurrentBank для управления счетами и выполнения переводов между ними.
Класс должен предоставлять методы createAccount для создания нового счета и transfer для выполнения
переводов между счетами.

Переводы между счетами должны быть атомарными, чтобы избежать ситуаций, когда одна часть транзакции
выполняется успешно, а другая нет.

Реализуйте метод getTotalBalance, который возвращает общий баланс всех счетов в банке.

public class ConcurrentBankExample {
    public static void main(String[] args) {
        ConcurrentBank bank = new ConcurrentBank();

        // Создание счетов
        BankAccount account1 = bank.createAccount(1000);
        BankAccount account2 = bank.createAccount(500);

        // Перевод между счетами
        Thread transferThread1 = new Thread(() -> bank.transfer(account1, account2, 200));
        Thread transferThread2 = new Thread(() -> bank.transfer(account2, account1, 100));

        transferThread1.start();
        transferThread2.start();

        try {
            transferThread1.join();
            transferThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вывод общего баланса
        System.out.println("Total balance: " + bank.getTotalBalance());
    }
}*/

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentBank {

    private final List<BankAccount> accounts = new ArrayList<>();

    private final AtomicLong idGenerator = new AtomicLong();

    public BankAccount createAccount(BigDecimal balance) {
        BankAccount account = new BankAccount(idGenerator.incrementAndGet(), balance);
        accounts.add(account);
        return account;
    }

    public synchronized void transfer(BankAccount from, BankAccount to, BigDecimal amount) {
        from.withdraw(amount);
        to.deposit(amount);
    }

    public synchronized BigDecimal getTotalBalance() {
        return accounts
                .stream()
                .map(BankAccount::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
