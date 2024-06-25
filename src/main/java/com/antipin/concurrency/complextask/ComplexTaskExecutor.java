package com.antipin.concurrency.complextask;

/*В этой задаче мы будем использовать CyclicBarrier и ExecutorService для синхронизации нескольких
потоков, выполняющих сложную задачу, и затем ожидающих, пока все потоки завершат выполнение,
чтобы объединить результаты.

Создайте класс ComplexTask, представляющий сложную задачу, которую несколько потоков будут выполнять.
В каждой задаче реализуйте метод execute(), который выполняет часть сложной задачи.

Создайте класс ComplexTaskExecutor, в котором будет использоваться CyclicBarrier и ExecutorService
 для синхронизации выполнения задач. Реализуйте метод executeTasks(int numberOfTasks),
 который создает пул потоков и назначает каждому потоку экземпляр сложной задачи для выполнения.
 Затем используйте CyclicBarrier для ожидания завершения всех потоков и объединения результатов
 их работы. В методе main создайте экземпляр ComplexTaskExecutor и вызовите метод executeTasks
 с несколькими задачами для выполнения.*/

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class ComplexTaskExecutor {

    private final CyclicBarrier barrier;

    public ComplexTaskExecutor(int numberOfTasks) {
        barrier = new CyclicBarrier(numberOfTasks, () -> System.out.println("all tasks are done"));
    }

    public void executeTasks(int numberOfTasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);
        Stream.generate(ComplexTask::new)
              .limit(numberOfTasks)
              .forEach(executorService::submit);
        executorService.shutdown();
    }

    class ComplexTask implements Runnable {

        @Override
        public void run() {
            Random random = new Random();
            try {
                Thread.sleep(random.nextInt(2000));
                System.out.println("Number of waiting tasks: " + barrier.getNumberWaiting());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task is done, waiting for barrier...");
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
