package com.example.demo.service;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import java.util.List;

public interface EmployeeService {
  List<EmployeeResponse> findAll();
  EmployeeResponse findById(Long id);
  EmployeeResponse create(EmployeeRequest req);
  EmployeeResponse update(Long id, EmployeeRequest req);
  void delete(Long id);
}
