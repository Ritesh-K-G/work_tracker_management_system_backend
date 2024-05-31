package com.springBootProject.work_tracker_management_system.model;

import com.springBootProject.work_tracker_management_system.dataTransferObject.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Document("Task")
@Data
@AllArgsConstructor
public class Project {

    @Id
    private String id;
    private String title;
    private String description;
    private Boolean edited;
    private String managerId;
    private List<String> collaboratorIds;
    private TaskStatus status;
    private List<Comment> commentList;
    private LocalDateTime assignedOn;
    private LocalDateTime deadline;
    private LocalDateTime completedOn;
    private LocalDateTime lastStatusUpdateOn;

    public Project() {
        ZoneId zid = ZoneId.of("Asia/Kolkata");
        this.title = "";
        this.description = "";
        this.managerId = "";
        this.edited = false;
        this.collaboratorIds = new ArrayList<>();
        this.status = TaskStatus.ASSIGNED;
        this.commentList = new ArrayList<>();
        this.assignedOn = LocalDateTime.now(zid);
        this.deadline = LocalDateTime.now(zid);
        this.completedOn = null;
        this.lastStatusUpdateOn = LocalDateTime.now(zid);
    }

    public Project(ProjectDTO projectDTO) {
        ZoneId zid = ZoneId.of("Asia/Kolkata");
        this.title = projectDTO.getTitle();
        this.description = projectDTO.getDescription();
        this.managerId = projectDTO.getManagerId();
        this.collaboratorIds = projectDTO.getCollaboratorIds();
        this.status = projectDTO.getStatus();
        this.commentList = new ArrayList<>();
        this.edited = false;
        this.assignedOn = projectDTO.getAssignedOn();
        this.deadline = projectDTO.getDeadline();
        this.completedOn = null;
        this.lastStatusUpdateOn = LocalDateTime.now(zid);
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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
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

    public LocalDateTime getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(LocalDateTime completedOn) {
        this.completedOn = completedOn;
    }

    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    public LocalDateTime getLastStatusUpdateOn() {
        return lastStatusUpdateOn;
    }

    public void setLastStatusUpdateOn(LocalDateTime lastStatusUpdateOn) {
        this.lastStatusUpdateOn = lastStatusUpdateOn;
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }

    public void pushCollaborator(String CollaboratorId) {
        this.collaboratorIds.add(CollaboratorId);
    }
}