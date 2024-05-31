package com.springBootProject.work_tracker_management_system.dataTransferObject;

import java.time.LocalDateTime;

public class CommentDTO {
    private String taskId;
    private String commentatorName;
    private String comment;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCommentatorName() {
        return commentatorName;
    }

    public void setCommentatorName(String commentatorName) {
        this.commentatorName = commentatorName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
