package com.shivam.async;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WhyNotFuture2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<List<Integer>> future1 = executorService.submit(() -> {
            System.out.println("Thread : "+ Thread.currentThread().getName());
            return Arrays.asList(1, 2, 3, 4, 5);
        });

        Future<List<Integer>> future2 = executorService.submit(() -> {
            System.out.println("Thread : "+ Thread.currentThread().getName());
            return Arrays.asList(1, 2, 3, 4, 5);
        });

        Future<List<Integer>> future3 = executorService.submit(() -> {
            System.out.println("Thread : "+ Thread.currentThread().getName());
            return Arrays.asList(1, 2, 3, 4, 5);
        });

        future1.get(); // blocking operation
        future2.get(); // blocking operation
        future3.get(); // blocking operation
        // we can not combine multiple future together
    }
}
