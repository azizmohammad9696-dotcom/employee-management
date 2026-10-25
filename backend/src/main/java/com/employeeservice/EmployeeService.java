package com.employeeservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.employeemodel.Employee;
import com.employeerepositroty.EmployeeRepository;




@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee create(Employee employee) {
        employee.setId(null);
        return repository.save(employee);
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Employee not found: " + id));
    }

    public Employee update(Long id, Employee updated) {
        Employee employee = getById(id);

        employee.setName(updated.getName());
        employee.setEmail(updated.getEmail());
        employee.setDepartment(updated.getDepartment());
        employee.setPosition(updated.getPosition());
        employee.setStatus(updated.getStatus());

        return repository.save(employee);
    }

    public void delete(Long id) {
        Employee employee = getById(id);
        repository.delete(employee);
    }

    public List<String> getDepartments() {
        return repository.findAll().stream()
                .map(Employee::getDepartment)
                .filter(d -> d != null && !d.isBlank())
                .distinct()
                .sorted()
                .toList();
    }
}