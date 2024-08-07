package com.shivam.async;

import com.shivam.database.EmployeeDatabase;
import com.shivam.entity.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SupplyAsyncDemo {

    public List<Employee> getEmployees() throws ExecutionException, InterruptedException {
        EmployeeDatabase employeeDatabase = new EmployeeDatabase();
        Executor executor = Executors.newCachedThreadPool();
        CompletableFuture<List<Employee>> listCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Executed by "+Thread.currentThread().getName()); // by default use forkJoin pool
            return employeeDatabase.fetchEmployees();
        }, executor);
        return listCompletableFuture.get();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SupplyAsyncDemo supplyAsyncDemo = new SupplyAsyncDemo();
        List<Employee> employees = supplyAsyncDemo.getEmployees();
        employees.forEach(System.out::println);
    }
}
