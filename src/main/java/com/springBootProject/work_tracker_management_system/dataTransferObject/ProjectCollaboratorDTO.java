package com.springBootProject.work_tracker_management_system.dataTransferObject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectCollaboratorDTO {
    private String projectId;
    private String collaboratorId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(String collaboratorId) {
        this.collaboratorId = collaboratorId;
    }
}
