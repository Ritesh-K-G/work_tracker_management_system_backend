package com.springBootProject.work_tracker_management_system.repository;

import com.springBootProject.work_tracker_management_system.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    @Query("{ 'email' : ?0 }")
    Optional<Employee> findByEmail(String email);
}
