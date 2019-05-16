package com.smartcity.dto;

public class BudgetDto {

    Long value;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public BudgetDto(Long value) {
        this.value = value;
    }

    public BudgetDto() {

    }
}
