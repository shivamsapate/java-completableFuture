package com.shivam.async;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivam.entity.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RunAsyncDemo {

    public void saveEmployeeData(File jsonFile) throws ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(() -> {
            try {
                List<Employee> employees = mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
                });
                // write logic to save data to database
                // repo.save(employees);
                System.out.println("Thread - " + Thread.currentThread().getName()); // internally uses fork join pool
                employees.forEach(System.out::println);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Void unused = runAsyncFuture.get();
    }

    public void saveEmployeeDataWithExecutor(File jsonFile) throws ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        Executor executor = Executors.newFixedThreadPool(15);
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(() -> {
            try {
                List<Employee> employees = mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
                });
                // write logic to save data to database
                // repo.save(employees);
                System.out.println("Thread - " + Thread.currentThread().getName()); // will print thread pool thread instead of fork join
                System.out.println(employees.size());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, executor);
        Void unused = runAsyncFuture.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RunAsyncDemo runAsyncDemo = new RunAsyncDemo();
        runAsyncDemo.saveEmployeeData(new File("mock.json"));
        runAsyncDemo.saveEmployeeDataWithExecutor(new File("mock.json"));

    }

}
