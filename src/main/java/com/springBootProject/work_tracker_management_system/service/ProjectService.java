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
            Project createdProject = projectRepository.save(project);
            String projectId = createdProject.getId();
            List<String> collaboratorIds = createdProject.getCollaboratorIds();
            Optional<Employee> managerData = employeeRepository.findById(managerId);
            if (managerData.isPresent()) {
                System.out.println(managerData.get());
                Employee manager = managerData.get();
                manager.pushManaging(projectId);
                employeeRepository.save(manager);
                for (String collaboratorId : collaboratorIds) {
                    Optional<Employee> collaboratorData = employeeRepository.findById(collaboratorId);
                    if (collaboratorData.isPresent()) {
                        Employee collaborator = collaboratorData.get();
                        collaborator.pushAssigned(projectId);
                        employeeRepository.save(collaborator);
                    } else {
                        // TO DO
                        System.out.println("Employee not found, " + collaboratorId);
                    }
                }
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
                System.out.println(employee);
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
                    project.setLastStatusUpdateOn(LocalDateTime.now(zid));
                } else if (currentStatus == TaskStatus.RECEIVED) {
                    project.setStatus(TaskStatus.IN_PROGRESS);
                    project.setLastStatusUpdateOn(LocalDateTime.now(zid));
                } else if (currentStatus == TaskStatus.IN_PROGRESS) {
                    project.setStatus(TaskStatus.COMPLETED);
                    project.setLastStatusUpdateOn(LocalDateTime.now(zid));
                    project.setCompletedOn(LocalDateTime.now(zid));
                } else if (currentStatus == TaskStatus.COMPLETED) {
                    project.setStatus(TaskStatus.ACCEPTED);
                    project.setLastStatusUpdateOn(LocalDateTime.now(zid));
                } else {
                    // Task has already been closed
                }
                projectRepository.save(project);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Project Status Updated";
    }

    public String addComment(CommentDTO commentDTO) {
        try{
            Comment comment = new Comment(commentDTO);
            String projectId = commentDTO.getTaskId();
            Optional<Project> projectData = projectRepository.findById(projectId);
            if (projectData.isPresent()) {
                Project project = projectData.get();
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
            Optional<Project> projectData = projectRepository.findById(projectId);
            if (projectData.isPresent()) {
                ZoneId zid = ZoneId.of("Asia/Kolkata");
                Project project = projectData.get();
                project.setEdited(true);
                project.setTitle(projectDTO.getTitle());
                project.setDescription(projectDTO.getDescription());
                project.setDeadline(projectDTO.getDeadline());
                project.setLastStatusUpdateOn(LocalDateTime.now(zid));
                projectRepository.save(project);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Project Details Updated";
    }

    public String addCollaborator(ProjectCollaboratorDTO projectCollaboratorDTO) {
        try {
            Optional<Project> projectData = projectRepository.findById(projectCollaboratorDTO.getProjectId());
            Optional<Employee> employeeData = employeeRepository.findById(projectCollaboratorDTO.getCollaboratorId());
            if (projectData.isPresent() && employeeData.isPresent()) {
                Project project = projectData.get();
                Employee employee = employeeData.get();
                if (project.checkCollaborator(projectCollaboratorDTO.getCollaboratorId())) {
                    System.out.println("Collaborator already exist");
                    return "Collaborator already exist";
                }
                project.pushCollaborator(projectCollaboratorDTO.getCollaboratorId());
                projectRepository.save(project);
                employee.pushAssigned(project.getId());
                employeeRepository.save(employee);
            } else {
                System.out.println("Project/Collaborator doesn't exist");
                return "Collaborator cannot be added";
            }
        } catch (Exception e) {
            System.out.println(e);
            return "Collaborator cannot be added";
        }
        return "Collaborator Added";
    }

    public String removeCollaborator(ProjectCollaboratorDTO projectCollaboratorDTO) {
        try {
            Optional<Project> projectData = projectRepository.findById(projectCollaboratorDTO.getProjectId());
            Optional<Employee> employeeData = employeeRepository.findById(projectCollaboratorDTO.getCollaboratorId());
            if (projectData.isPresent() && employeeData.isPresent()) {
                Project project = projectData.get();
                Employee employee = employeeData.get();
                if (!project.checkCollaborator(projectCollaboratorDTO.getCollaboratorId())) {
                    System.out.println("Collaborator doesn't exist int this Project");
                    return "Collaborator doesn't exist in this Project";
                }
                project.removeCollaborator(projectCollaboratorDTO.getCollaboratorId());
                projectRepository.save(project);
                employee.removeAssigned(project.getId());
                employeeRepository.save(employee);
            } else {
                System.out.println("Project/Collaborator doesn't exist");
                return "Collaborator cannot be removed";
            }
        } catch (Exception e) {
            System.out.println(e);
            return "Collaborator cannot be removed";
        }
        return "Collaborator Removed";
    }
}
