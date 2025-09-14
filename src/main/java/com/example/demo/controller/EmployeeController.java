package com.example.demo.controller;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("employee", new EmployeeRequest());
        model.addAttribute("title", "Add Employee");
        return "employees/form";
    }

    @PostMapping
    public String create(@ModelAttribute("employee") @Valid EmployeeRequest employeeRequest) {
        employeeService.create(employeeRequest);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        EmployeeResponse employeeResponse = employeeService.findById(id);
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName(employeeResponse.getName());
        employeeRequest.setEmail(employeeResponse.getEmail());
        employeeRequest.setSalary(employeeResponse.getSalary());
        model.addAttribute("employee", employeeRequest);
        model.addAttribute("id", id);
        model.addAttribute("title", "Edit Employee");
        return "employees/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("employee") @Valid EmployeeRequest employeeRequest) {
        employeeService.update(id, employeeRequest);
        return "redirect:/employees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return "redirect:/employees";
    }
}
