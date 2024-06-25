package com.antipin.concurrency.concurrentbank;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private final Long id;

    private volatile BigDecimal balance;

    private final Lock lock;

    BankAccount(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
        lock = new ReentrantLock();
    }

    public BigDecimal deposit(BigDecimal amount) {
        lock.lock();
        try {
            balance = balance.add(amount);
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public BigDecimal withdraw(BigDecimal amount) {
        lock.lock();
        try {
            balance = balance.subtract(amount);
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
