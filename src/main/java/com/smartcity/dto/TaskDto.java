package com.smartcity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.dto.transfer.NewRecord;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class TaskDto {
    @Null(groups = {NewRecord.class})
    @NotNull(groups = {ExistingRecord.class})
    private Long id;

    @NotBlank(message = "Please, provide a title")
    @Pattern(regexp = "[a-zA-Z]", message = "Please, provide a valid title")
    private String title;

    @NotBlank(message = "Please, provide a title")
    private String description;

    @NotNull(message = "Please, provide an accomplishment date")
    @Future(message = "Accomplishment date should be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime deadlineDate;

    //    TODO: Validate this field!
    private String taskStatus;

    @NotNull(message = "Please, add a budget")
    private Long budget;

    //    TODO: Validate this field!
    private Long approvedBudget;

    //    TODO: Validate this field!
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    //    TODO: Validate this field!
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedAt;

    //    TODO: Validate this field!
    private Long usersOrganizationsId;

    public TaskDto(Long id, String title, String description, LocalDateTime deadlineDate, String taskStatus,
                   Long budget, Long approvedBudget, LocalDateTime createdAt,
                   LocalDateTime updatedAt, Long usersOrganizationsId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadlineDate = deadlineDate;
        this.taskStatus = taskStatus;
        this.budget = budget;
        this.approvedBudget = approvedBudget;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.usersOrganizationsId = usersOrganizationsId;
    }

    public TaskDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDateTime deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Long getApprovedBudget() {
        return approvedBudget;
    }

    public void setApprovedBudget(Long approvedBudget) {
        this.approvedBudget = approvedBudget;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUsersOrganizationsId() {
        return usersOrganizationsId;
    }

    public void setUsersOrganizationsId(Long usersOrganizationsId) {
        this.usersOrganizationsId = usersOrganizationsId;
    }
}