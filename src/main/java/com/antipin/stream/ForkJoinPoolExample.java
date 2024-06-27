package com.antipin.stream;

/*Рассмотрим задачу вычисления факториала числа с использованием ForkJoinPool.
Факториал числа n обозначается как n! и вычисляется как произведение всех положительных
целых чисел от 1 до n.

Реализуйте класс FactorialTask, который расширяет RecursiveTask. Этот класс будет выполнять
рекурсивное вычисление факториала числа.
В конструкторе FactorialTask передайте число n, факториал которого нужно вычислить.
В методе compute() разбейте задачу на подзадачи и используйте fork() для их асинхронного выполнения.
Используйте join() для получения результатов подзадач и комбинирования их для получения общего результата.
В основном методе создайте экземпляр FactorialTask с числом, для которого нужно вычислить факториал,
и запустите его в ForkJoinPool.
Выведите результат вычисления факториала.*/

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolExample {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FactorialTask task = new FactorialTask(20L);
        System.out.println(pool.invoke(task));
    }
}

class FactorialTask extends RecursiveTask<Long> {

    final Long number;

    public FactorialTask(Long number) {
        this.number = number;
    }

    @Override
    protected Long compute() {
        System.out.println(Thread.currentThread().getName());
        if (number == 1) {
            return number;
        }
        FactorialTask task = new FactorialTask(number - 1);
        task.fork();
        return task.join() * number;
    }
}
