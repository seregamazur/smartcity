package com.smartcity.domain;

import java.util.Objects;

public class Budget {

    Long value;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Budget(Long value) {
        this.value = value;
    }

    public Budget() {

    }

    @Override
    public String toString() {
        return "Budget{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return Objects.equals(value, budget.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }
}
