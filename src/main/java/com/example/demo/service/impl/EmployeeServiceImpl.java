package com.example.demo.service.impl;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> findAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found: " + id));
        return toResponse(employee);
    }

    @Override
    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        if (repository.existsByEmail(employeeRequest.getEmail())) {
            throw new ConflictException("Email already in use: " + employeeRequest.getEmail());
        }
        Employee employee = new Employee();
        employee.setName(employeeRequest.getName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setSalary(employeeRequest.getSalary());
        employee = repository.save(employee);
        return toResponse(employee);
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found: " + id));

        if (!employee.getEmail().equalsIgnoreCase(employeeRequest.getEmail())
                && repository.existsByEmail(employeeRequest.getEmail())) {
            throw new ConflictException("Email already in use: " + employeeRequest.getEmail());
        }

        employee.setName(employeeRequest.getName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setSalary(employeeRequest.getSalary());
        employee = repository.save(employee);
        return toResponse(employee);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Employee not found: " + id);
        }
        repository.deleteById(id);
    }

    private EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getSalary(),
                employee.getCreatedAt()
        );
    }
}
