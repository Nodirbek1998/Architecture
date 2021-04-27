package uz.cas.controllersestem.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ReqComment {
    @NotBlank
    private String comment;

    private Integer userId;
    @NotNull
    private Integer projectId;

    public ReqComment() {
    }

    public ReqComment(String comment, Integer userId, Integer projectId) {
        this.comment = comment;
        this.userId = userId;
        this.projectId = projectId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
