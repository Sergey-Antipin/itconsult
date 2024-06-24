package com.antipin.concurrency;

/*Предположим, у вас есть пул потоков, и вы хотите реализовать блокирующую очередь для передачи
задач между потоками. Создайте класс BlockingQueue, который будет обеспечивать безопасное
добавление и извлечение элементов между производителями и потребителями в контексте пула потоков.

Класс BlockingQueue должен содержать методы enqueue() для добавления элемента в очередь и
dequeue() для извлечения элемента. Если очередь пуста, dequeue() должен блокировать вызывающий поток
до появления нового элемента.

очередь должна иметь фиксированный размер.

Используйте механизмы wait() и notify() для координации между производителями и потребителями.
Реализуйте метод size(), который возвращает текущий размер очереди.*/

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<E> {

    private final Object[] items;

    private int takeIndex;

    private int putIndex;

    private int count;

    private final Lock lock;

    public BlockingQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        items = new Object[capacity];
        lock = new ReentrantLock();
    }

    public E dequeue() {
        lock.lock();
        try {
            while (count == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            @SuppressWarnings("unchecked")
            E element = (E) items[takeIndex];
            if (++takeIndex == items.length) {
                takeIndex = 0;
            }
            count--;
            notify();
            return element;
        } finally {
            lock.unlock();
        }
    }

    public void enqueue(E element) {
        lock.lock();
        try {
            while (count == items.length) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            items[putIndex] = element;
            if (++putIndex == items.length) {
                putIndex = 0;
            }
            count++;
            notify();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}
