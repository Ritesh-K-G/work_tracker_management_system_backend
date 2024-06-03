package com.springBootProject.work_tracker_management_system.model;

import com.springBootProject.work_tracker_management_system.dataTransferObject.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;

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
    private String managerName;
    private List<String> collaboratorIds;
    private List<String> collaboratorNames;
    private TaskStatus status;
    private List<Pair<String, LocalDateTime>> updatesTimeline;
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
        this.managerName = "";
        this.edited = false;
        this.collaboratorIds = new ArrayList<>();
        this.collaboratorNames = new ArrayList<>();
        this.status = TaskStatus.ASSIGNED;
        this.updatesTimeline = new ArrayList<>();
        this.commentList = new ArrayList<>();
        this.assignedOn = LocalDateTime.now(zid);
        this.deadline = LocalDateTime.now(zid);
        this.completedOn = null;
        this.lastStatusUpdateOn = LocalDateTime.now(zid);
        this.addStatusUpdateTimeline(TaskStatus.ASSIGNED.toString(), this.lastStatusUpdateOn);
    }

    public Project(ProjectDTO projectDTO) {
        ZoneId zid = ZoneId.of("Asia/Kolkata");
        this.title = projectDTO.getTitle();
        this.description = projectDTO.getDescription();
        this.managerId = projectDTO.getManagerId();
        this.managerName = projectDTO.getManagerName();
        this.collaboratorIds = projectDTO.getCollaboratorIds();
        this.collaboratorNames = new ArrayList<>();
        this.status = projectDTO.getStatus();
        this.updatesTimeline = new ArrayList<>();
        this.commentList = new ArrayList<>();
        this.edited = false;
        this.assignedOn = LocalDateTime.now(zid);
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

    public List<String> getCollaboratorNames() {
        return collaboratorNames;
    }

    public void setCollaboratorNames(List<String> collaboratorNames) {
        this.collaboratorNames = collaboratorNames;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public List<Pair<String, LocalDateTime>> getUpdatesTimeline() {
        return updatesTimeline;
    }

    public void setUpdatesTimeline(List<Pair<String, LocalDateTime>> updatesTimeline) {
        this.updatesTimeline = updatesTimeline;
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

    public void removeCollaborator(String collaboratorId) {
        this.collaboratorIds.remove(collaboratorId);
    }

    public boolean checkCollaborator(String collaboratorId) {
        return this.collaboratorIds.contains(collaboratorId);
    }

    public void addStatusUpdateTimeline(String update, LocalDateTime time) {
        this.updatesTimeline.add(Pair.of(update, time));
    }

    public void pushCollaboratorName(String name) {
        this.collaboratorNames.add(name);
    }

    public void removeCollaboratorName(String name) {
        this.collaboratorNames.remove(name);
    }
}