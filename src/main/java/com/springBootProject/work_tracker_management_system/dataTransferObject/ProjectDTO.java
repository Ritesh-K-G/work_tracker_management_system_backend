package com.springBootProject.work_tracker_management_system.dataTransferObject;

import com.springBootProject.work_tracker_management_system.model.TaskStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Document("Task")
@Data
public class ProjectDTO {

    @Id
    private String id;
    private String title;
    private String description;
    private String managerId;
    private String managerName;
    private List<String> collaboratorIds;
    private TaskStatus status;
    private LocalDateTime assignedOn;
    private LocalDateTime deadline;

    public ProjectDTO() {
        ZoneId zid = ZoneId.of("Asia/Kolkata");
        this.title = "";
        this.description = "";
        this.managerId = "";
        this.managerName = "";
        this.collaboratorIds = new ArrayList<>();
        this.status = TaskStatus.ASSIGNED;
        this.assignedOn = LocalDateTime.now(zid);
        this.deadline = LocalDateTime.now(zid);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public List<String> getCollaboratorIds() {
        return collaboratorIds;
    }

    public void setCollaboratorIds(List<String> collaboratorIds) {
        this.collaboratorIds = collaboratorIds;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getAssignedOn() {
        return assignedOn;
    }

    public void setAssignedOn(LocalDateTime assignedOn) {
        this.assignedOn = assignedOn;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
