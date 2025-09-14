package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class EmployeeResponse {
    private Long id;
    private String name;
    private String email;
    private BigDecimal salary;
    private Instant createdAt;

    public EmployeeResponse(Long id, String name, String email, BigDecimal salary, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
