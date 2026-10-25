package com.employeecontroller;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.employeemodel.Employee;
import com.employeeservice.EmployeeService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @GetMapping("/employees")
    public List<Employee> getAll() {
        return service.getAll();
    }

    @GetMapping("/employees/{id}")
    public Employee getById(@PathVariable Long id) {
        try {
            return service.getById(id);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PutMapping("/employees/{id}")
    public Employee update(
            @PathVariable Long id,
            @RequestBody Employee employee) {
        try {
            return service.update(id, employee);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            service.delete(id);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping("/departments")
    public List<String> getDepartments() {
        return service.getDepartments();
    }

    @GetMapping("/dashboard/stats")
    public Map<String, Long> getStats() {
        List<Employee> employees = service.getAll();

        long total = employees.size();
        long active = employees.stream()
                .filter(e -> "Active".equalsIgnoreCase(e.getStatus()))
                .count();
        long departments = service.getDepartments().size();

        return Map.of(
            "totalEmployees", total,
            "activeEmployees", active,
            "departments", departments
        );
    }
}
