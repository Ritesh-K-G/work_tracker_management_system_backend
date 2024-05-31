package com.springBootProject.work_tracker_management_system.service;

import com.springBootProject.work_tracker_management_system.dataTransferObject.EmployeeDTO;
import com.springBootProject.work_tracker_management_system.model.Employee;
import com.springBootProject.work_tracker_management_system.model.Project;
import com.springBootProject.work_tracker_management_system.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public String createEmployee(EmployeeDTO employeeDTO) {
        try {
            Employee employee = new Employee(employeeDTO);
            employeeRepository.save(employee);
        } catch (Exception e) {
            // TO DO
            System.out.println(e);
        }
        return "Employee Created";
    }

    public List<Employee> findAll() {
        List<Employee> allEmployees = new ArrayList<>();
        try{
            allEmployees = employeeRepository.findAll();
        } catch (Exception e) {
            // TO DO
            System.out.println(e);
        }
        return allEmployees;
    }

    public Employee findById(String Id) {
        Employee employee = new Employee();
        try {
            Optional<Employee> employeeData = employeeRepository.findById(Id);
            if (employeeData.isPresent()) {
                employee = employeeData.get();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return employee;
    }
}
