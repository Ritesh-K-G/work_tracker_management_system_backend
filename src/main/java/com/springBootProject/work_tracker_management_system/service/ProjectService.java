package com.springBootProject.work_tracker_management_system.service;

import com.springBootProject.work_tracker_management_system.dataTransferObject.CommentDTO;
import com.springBootProject.work_tracker_management_system.dataTransferObject.ProjectCollaboratorDTO;
import com.springBootProject.work_tracker_management_system.dataTransferObject.ProjectDTO;
import com.springBootProject.work_tracker_management_system.model.Comment;
import com.springBootProject.work_tracker_management_system.model.Employee;
import com.springBootProject.work_tracker_management_system.model.Project;
import com.springBootProject.work_tracker_management_system.model.TaskStatus;
import com.springBootProject.work_tracker_management_system.repository.EmployeeRepository;
import com.springBootProject.work_tracker_management_system.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.ZoneId;

@Service
public class ProjectService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public String createProject(ProjectDTO projectDTO) {
        Project project = new Project(projectDTO);
        try {
            String managerId = project.getManagerId();
            ZoneId zid = ZoneId.of("Asia/Kolkata");
            project.addStatusUpdateTimeline("ASSIGNED", LocalDateTime.now(zid));
            Project createdProject = projectRepository.save(project);
            List<String> collaboratorNames = new ArrayList<>();
            String projectId = createdProject.getId();
            List<String> collaboratorIds = createdProject.getCollaboratorIds();
            Optional<Employee> managerData = employeeRepository.findById(managerId);
            if (managerData.isPresent()) {
                Employee manager = managerData.get();
                manager.pushManaging(projectId);
                employeeRepository.save(manager);
                for (String collaboratorId : collaboratorIds) {
                    Optional<Employee> collaboratorData = employeeRepository.findById(collaboratorId);
                    if (collaboratorData.isPresent()) {
                        Employee collaborator = collaboratorData.get();
                        collaboratorNames.add(collaborator.getName());
                        collaborator.pushAssigned(projectId);
                        employeeRepository.save(collaborator);
                    } else {
                        // TO DO
                        System.out.println("Employee not found, " + collaboratorId);
                    }
                }
                createdProject.setManagerName(manager.getName());
                createdProject.setCollaboratorNames(collaboratorNames);
                projectRepository.save(createdProject);
            } else {
                // TO DO
                System.out.println("Manager not found, " + managerId);
            }
        } catch(Exception e) {
            // TO DO
            System.out.println(e);
        }
        return "Project Created";
    }

    public List<Project> findManagingProjects(String Id) {
        List<Project> managingProjects = new ArrayList<>();
        try {
            Optional<Employee> employeeData = employeeRepository.findById(Id);
            if (employeeData.isPresent()) {
                Employee employee = employeeData.get();
                List<String> managingProjectsId = employee.getManaging();
                for (String projectID : managingProjectsId) {
                    Optional<Project> projectData = projectRepository.findById(projectID);
                    if (projectData.isPresent()) {
                        managingProjects.add(projectData.get());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return managingProjects;
    }

    public List<Project> findAssignedProjects(String Id) {
        List<Project> assignedProjects = new ArrayList<>();
        try {
            Optional<Employee> employeeData = employeeRepository.findById(Id);
            if (employeeData.isPresent()) {
                Employee employee = employeeData.get();
                List<String> assignedProjectsId = employee.getAssigned();
                for (String projectID : assignedProjectsId) {
                    Optional<Project> projectData = projectRepository.findById(projectID);
                    if (projectData.isPresent()) {
                        assignedProjects.add(projectData.get());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return assignedProjects;
    }

    public String updateProjectStatus(String Id) {
        try {
            Optional<Project> projectData = projectRepository.findById(Id);
            ZoneId zid = ZoneId.of("Asia/Kolkata");
            if (projectData.isPresent()) {
                Project project = projectData.get();
                TaskStatus currentStatus = project.getStatus();
                if (currentStatus == TaskStatus.ASSIGNED) {
                    project.setStatus(TaskStatus.RECEIVED);
                } else if (currentStatus == TaskStatus.RECEIVED) {
                    project.setStatus(TaskStatus.IN_PROGRESS);
                } else if (currentStatus == TaskStatus.IN_PROGRESS) {
                    project.setStatus(TaskStatus.COMPLETED);
                    project.setCompletedOn(LocalDateTime.now(zid));
                } else if (currentStatus == TaskStatus.COMPLETED) {
                    project.setStatus(TaskStatus.ACCEPTED);
                } else {
                    // Task has already been closed
                    return "Project has already been closed";
                }
                project.setLastStatusUpdateOn(LocalDateTime.now(zid));
                project.addStatusUpdateTimeline(project.getStatus().toString(), LocalDateTime.now(zid));
                projectRepository.save(project);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Project Status Updated";
    }

    public String addComment(CommentDTO commentDTO, String id) {
        try{
            Comment comment = new Comment(commentDTO);
            String projectId = commentDTO.getTaskId();
            Optional<Employee> employeeData = employeeRepository.findById(id);
            Optional<Project> projectData = projectRepository.findById(projectId);
            if (projectData.isPresent() && employeeData.isPresent()) {
                Project project = projectData.get();
                Employee employee = employeeData.get();
                comment.setCommentatorName(employee.getName());
                project.addComment(comment);
                projectRepository.save(project);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Comment added";
    }

    public String updateTask(ProjectDTO projectDTO) {
        try {
            String projectId = projectDTO.getId();
            System.out.println(projectDTO);
            Optional<Project> projectData = projectRepository.findById(projectId);
            if (projectData.isPresent()) {
                Project project = projectData.get();
                // add new collaborators
                for (String collaboratorId: projectDTO.getCollaboratorIds()) {
                    if (!project.checkCollaborator(collaboratorId)) {
                        addCollaborator(projectId, collaboratorId);
                    }
                }
                // remove excluded collaborators
                for (String collaboratorId: project.getCollaboratorIds()) {
                    if (!projectDTO.getCollaboratorIds().contains(collaboratorId)) {
                        removeCollaborator(projectId, collaboratorId);
                    }
                }
                projectData = projectRepository.findById(projectId);
                if (projectData.isPresent()) {
                    project = projectData.get();
                    ZoneId zid = ZoneId.of("Asia/Kolkata");
                    project.setEdited(true);
                    project.setTitle(projectDTO.getTitle());
                    project.setDescription(projectDTO.getDescription());
                    project.setDeadline(projectDTO.getDeadline());
                    project.setLastStatusUpdateOn(LocalDateTime.now(zid));
                    project.addStatusUpdateTimeline("Edited", LocalDateTime.now(zid));
                    projectRepository.save(project);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Project Details Updated";
    }

    public void addCollaborator(String projectId, String collaboratorId) {
        try {
            Optional<Project> projectData = projectRepository.findById(projectId);
            Optional<Employee> employeeData = employeeRepository.findById(collaboratorId);
            if (projectData.isPresent() && employeeData.isPresent()) {
                Project project = projectData.get();
                Employee employee = employeeData.get();
                if (project.checkCollaborator(collaboratorId)) {
                    System.out.println("Collaborator already exist");
                }
                project.pushCollaborator(collaboratorId);
                employee.pushAssigned(project.getId());
                project.pushCollaboratorName(employee.getName());
                projectRepository.save(project);
                employeeRepository.save(employee);
            } else {
                System.out.println("Project/Collaborator doesn't exist");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void removeCollaborator(String projectId, String collaboratorId) {
        try {
            Optional<Project> projectData = projectRepository.findById(projectId);
            Optional<Employee> employeeData = employeeRepository.findById(collaboratorId);
            if (projectData.isPresent() && employeeData.isPresent()) {
                Project project = projectData.get();
                Employee employee = employeeData.get();
                if (!project.checkCollaborator(collaboratorId)) {
                    System.out.println("Collaborator doesn't exist int this Project");
                }
                project.removeCollaborator(collaboratorId);
                employee.removeAssigned(project.getId());
                project.removeCollaboratorName(employee.getName());
                projectRepository.save(project);
                employeeRepository.save(employee);
            } else {
                System.out.println("Project/Collaborator doesn't exist");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void reAssign(String projectId) {
        Optional<Project> projectData = projectRepository.findById(projectId);
        if (projectData.isPresent()) {
            Project project = projectData.get();
            ZoneId zid = ZoneId.of("Asia/Kolkata");
            project.setEdited(true);
            project.setStatus(TaskStatus.ASSIGNED);
            project.addStatusUpdateTimeline("Reassigned", LocalDateTime.now(zid));
            projectRepository.save(project);
        }
    }
}
