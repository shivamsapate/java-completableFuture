package com.shivam.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivam.entity.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmployeeDatabase {

    public static List<Employee> fetchEmployees() {
        ObjectMapper mapper = new ObjectMapper();
        List<Employee> employees = null;
        try {
            employees = mapper.readValue(new File("mock.json"), new TypeReference<List<Employee>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    public static List<Employee> fetchUpdatedEmployees() {
        ObjectMapper mapper = new ObjectMapper();
        List<Employee> employees = null;
        try {
            employees = mapper.readValue(new File("updateMock.json"), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
}
