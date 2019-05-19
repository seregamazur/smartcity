package com.smartcity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.dto.transfer.NewRecord;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

public class TransactionDto {
    @Null(groups = {NewRecord.class})
    @NotNull(groups = {ExistingRecord.class})
    private Long id;
    //    TODO: Validate this field!
    private Long taskId;
    //    TODO: Validate this field!
    private Long currentBudget;
    //    TODO: Validate this field!
    private Long transactionBudget;
    //    TODO: Validate this field!
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;
    //    TODO: Validate this field!
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

    public TransactionDto() {
    }

    public TransactionDto(Long id, Long taskId, Long currentBudget,
                          Long transactionBudget, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.taskId = taskId;
        this.currentBudget = currentBudget;
        this.transactionBudget = transactionBudget;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCurrentBudget() {
        return currentBudget;
    }

    public void setCurrentBudget(Long currentBudget) {
        this.currentBudget = currentBudget;
    }

    public Long getTransactionBudget() {
        return transactionBudget;
    }

    public void setTransactionBudget(Long transactionBudget) {
        this.transactionBudget = transactionBudget;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }


}
