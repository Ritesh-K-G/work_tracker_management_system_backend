package com.springBootProject.work_tracker_management_system.model;

import com.springBootProject.work_tracker_management_system.dataTransferObject.CommentDTO;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class Comment {
    private String commentatorName;
    private String comment;
    private LocalDateTime time;

    public Comment(CommentDTO commentDTO) {
        this.comment = commentDTO.getComment();
        this.commentatorName = commentDTO.getCommentatorName();
        this.time = LocalDateTime.now();
    }

    public Comment() {
        this.comment = "";
        this.commentatorName = "";
        this.time = LocalDateTime.now();
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
