package com.shivam.async;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
// we can't stop future manually
public class WhyNotFuture1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<List<Integer>> future = executorService.submit(() -> {
            //suppose you are doing some api call and api has delay
            System.out.println("Thread " +  Thread.currentThread().getName());
            delay(1);

            return Arrays.asList(1, 2, 3, 4, 5); // it will block the output for complete one minute
        });

        List<Integer> list = future.get();
        System.out.println(list);
    }

    private static void delay(int min) {
        try {
            TimeUnit.MINUTES.sleep(min);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
