package com.smartcity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.dto.transfer.NewRecord;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

public class OrganizationDto {

    @Null(groups = {NewRecord.class},
            message = "This field must be empty due to auto generation")
    @NotNull(groups = {ExistingRecord.class},
            message = "This field can't be empty")
    private Long id;

    @NotBlank(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, provide a name")
    private String name;

    @NotBlank(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, provide an address")
    private String address;


    @Null(groups = {NewRecord.class, ExistingRecord.class},
            message = "This field must be empty due to auto generation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;

    @Null(groups = {NewRecord.class, ExistingRecord.class},
            message = "This field must be empty due to auto generation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

    public OrganizationDto() {

    }

    public OrganizationDto(Long id, String name, String address, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

}