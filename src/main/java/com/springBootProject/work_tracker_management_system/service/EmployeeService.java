package com.springBootProject.work_tracker_management_system.service;

import com.springBootProject.work_tracker_management_system.dataTransferObject.EmployeeDTO;
import com.springBootProject.work_tracker_management_system.model.Employee;
import com.springBootProject.work_tracker_management_system.model.Project;
import com.springBootProject.work_tracker_management_system.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public String createEmployee(EmployeeDTO employeeDTO) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(employeeDTO.getEmail()));
        List<Employee> employees = mongoTemplate.find(query, Employee.class);
        if (employees.isEmpty()) {
            try {
                Employee employee = new Employee(employeeDTO);
                employeeRepository.save(employee);
            } catch (Exception e) {
                // TO DO
                System.out.println(e);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee cannot be added");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee cannot be added");
        }
        return "Employee Created";
    }

    public String login(String email, String password) {
        String user_id = "no";
        System.out.println(email + " " + password);
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(email));
            List<Employee> employees = mongoTemplate.find(query, Employee.class);
            if (employees.isEmpty()) {
                return user_id;
            } else {
                Employee employee = employees.get(0);
                if (employee.getPassword().equals(password)) {
                    user_id = employee.getId();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return user_id;
    }

    public List<Employee> findAll(String id) {
        List<Employee> allEmployees = new ArrayList<>();
        Optional<Employee> manager = employeeRepository.findById(id);
        if (manager.isEmpty()) {
            return allEmployees;
        }
        Employee managerData = manager.get();
        if (managerData.getDesignation().equals("INTERN")) {
            return allEmployees;
        }
        try{
            List<Employee> employees = employeeRepository.findAll();
            for (Employee employee : employees) {
                if (!employee.getId().equals(id)) {
                    if (!employee.getDesignation().equals("ADMIN"))
                        allEmployees.add(employee);
                }
            }
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
