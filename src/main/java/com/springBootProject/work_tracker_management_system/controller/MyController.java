package com.springBootProject.work_tracker_management_system.controller;

import com.springBootProject.work_tracker_management_system.dataTransferObject.CommentDTO;
import com.springBootProject.work_tracker_management_system.dataTransferObject.EmployeeDTO;
import com.springBootProject.work_tracker_management_system.dataTransferObject.ProjectCollaboratorDTO;
import com.springBootProject.work_tracker_management_system.dataTransferObject.ProjectDTO;
import com.springBootProject.work_tracker_management_system.model.Employee;
import com.springBootProject.work_tracker_management_system.model.Project;
import com.springBootProject.work_tracker_management_system.service.EmployeeService;
import com.springBootProject.work_tracker_management_system.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
public class MyController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("createEmployee")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody EmployeeDTO employeeDTO) {
        String res = employeeService.createEmployee(employeeDTO);
        if (res.equals("Employee cannot be added")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee cannot be added");
        }
        return res;
    }

    @GetMapping("validate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String validate(@RequestParam String email, @RequestParam String password) {
        String res = employeeService.login(email, password);
        if (res.equals("no")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User email / password is incorrect");
        }
        return res;
    }

    @GetMapping("find")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findAllEmployee(@RequestParam String id) {
        return employeeService.findAll(id);
    }

    @PostMapping("createTask")
    @ResponseStatus(HttpStatus.CREATED)
    public String createTask(@RequestBody ProjectDTO task) {
        return projectService.createProject(task);
    }

    @GetMapping("getManagingTasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Project> findManagingTasks(@RequestParam String Id) {
        return projectService.findManagingProjects(Id);
    }

    @GetMapping("getAssignedTasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Project> findAssignedTasks(@RequestParam String Id) {
        return projectService.findAssignedProjects(Id);
    }

    @PutMapping("updateTaskStatus")
    @ResponseStatus(HttpStatus.OK)
    public String updateStatus(@RequestParam String Id) {
        return projectService.updateProjectStatus(Id);
    }

    @PostMapping("addComment")
    @ResponseStatus(HttpStatus.CREATED)
    public String  addComment(@RequestBody CommentDTO commentDTO, @RequestParam String id) {
        return projectService.addComment(commentDTO, id);
    }

    @GetMapping("findEmployee")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployee(@RequestParam String Id) {
        return employeeService.findById(Id);
    }

    @PutMapping("reassign")
    @ResponseStatus(HttpStatus.OK)
    public void reAssign(@RequestParam String id) {
        projectService.reAssign(id);
    }

    @PutMapping("updateTaskDetails")
    @ResponseStatus(HttpStatus.OK)
    public String updateTask(@RequestBody ProjectDTO projectDTO) {
        return projectService.updateTask(projectDTO);
    }
}