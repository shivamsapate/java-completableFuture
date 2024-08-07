package com.shivam.async;

import com.shivam.database.EmployeeDatabase;
import com.shivam.entity.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class EmployeeRemainderService {
    public void sendRemainderToEmployee() {
        //use of completed futures method
        // it uses default single thread for all the task
        CompletableFuture.supplyAsync(()->{
            System.out.println("fetch employees : "+ Thread.currentThread().getName());
            return EmployeeDatabase.fetchUpdatedEmployees();
        }).thenApply((employeesResult) -> {
            System.out.println("filter new joined employees : "+ Thread.currentThread().getName());
            return employeesResult.stream()
                    .filter(Employee::isNewJoiner)
                    .collect(Collectors.toList());
        }).thenApply((employeesResult) -> {
            System.out.println("filter new joined with training not completed employees : "+ Thread.currentThread().getName());
            return employeesResult.stream()
                    .filter(Employee::isLearningPending)
                    .collect(Collectors.toList());
        }).thenApply((employeeResult)->{
            System.out.println("Get all email of employees : "+ Thread.currentThread().getName());
            return employeeResult.stream().map(Employee::getEmail).collect(Collectors.toList());
        }).thenAccept((emails)-> {
            System.out.println("Sending email to employee : "+Thread.currentThread().getName());
            emails.forEach(EmployeeRemainderService::sendEmail);
        }).join();
    }

    public void sendRemainderToEmployeeImproved() {
        Executor executor = Executors.newFixedThreadPool(5);
        //use of completed futures method
        // it uses default single thread for all the task
        CompletableFuture.supplyAsync(()->{
            System.out.println("fetch employees : "+ Thread.currentThread().getName());
            return EmployeeDatabase.fetchUpdatedEmployees();
        },executor).thenApplyAsync((employeesResult) -> {
            System.out.println("filter new joined employees : "+ Thread.currentThread().getName());
            return employeesResult.stream()
                    .filter(Employee::isNewJoiner)
                    .collect(Collectors.toList());
        },executor).thenApplyAsync((employeesResult) -> {
            System.out.println("filter new joined with training not completed employees : "+ Thread.currentThread().getName());
            return employeesResult.stream()
                    .filter(Employee::isLearningPending)
                    .collect(Collectors.toList());
        },executor).thenApplyAsync((employeeResult)->{
            System.out.println("Get all email of employees : "+ Thread.currentThread().getName());
            return employeeResult.stream().map(Employee::getEmail).collect(Collectors.toList());
        },executor).thenAcceptAsync((emails)-> {
            System.out.println("Sending email to employee : "+Thread.currentThread().getName());
            emails.forEach(EmployeeRemainderService::sendEmail);
        },executor).join();
    }

    public static void sendEmail(String emailId) {
        System.out.println("sending remainder email to "+emailId);
    }


    public static void main(String[] args) {
        EmployeeRemainderService employeeRemainderService = new EmployeeRemainderService();
//        employeeRemainderService.sendRemainderToEmployee();
        employeeRemainderService.sendRemainderToEmployeeImproved();
    }
}
