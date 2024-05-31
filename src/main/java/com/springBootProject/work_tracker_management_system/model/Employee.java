package com.springBootProject.work_tracker_management_system.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(value = "Employee")
@Data
public class Employee {
    @Id
    private String id;
    private String email;
    private String password;
    private String name;
    private String designation;
    private List<String> assigned;
    private List<String> managing;

    public Employee() {
        this.name = "Employee Name";
        this.email = "user@email.com";
        this.password = "password";
        this.designation = String.valueOf(Designantion.INTERN);
        this.managing = new ArrayList<>();
        this.assigned = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<String> getAssigned() {
        return assigned;
    }

    public void setAssigned(List<String> assigned) {
        this.assigned = assigned;
    }

    public List<String> getManaging() {
        return managing;
    }

    public void setManaging(List<String> managing) {
        this.managing = managing;
    }

    public void pushManaging(String projectId) {
        this.managing.add(projectId);
    }

    public void pushAssigned(String projectId) {
        this.assigned.add(projectId);
    }
}
