
import { Component, OnInit } from '@angular/core';
import { Employee, EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css']
})
export class EmployeesComponent implements OnInit {

  // Stores employees received from the backend.
  employees: Employee[] = [];

  // Displays loading and error messages.
  isLoading = false;
  errorMessage = '';

  // Injects the employee API service.
  constructor(private employeeService: EmployeeService) {}

  // Loads employees when the page opens.
  ngOnInit(): void {
    this.loadEmployees();
  }

  // Requests the employee list from Spring Boot.
  loadEmployees(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.employeeService.getAll().subscribe({
      next: (data) => {
        this.employees = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading employees:', error);
        this.errorMessage =
          'Unable to load employees. Check the backend server.';
        this.isLoading = false;
      }
    });
  }
}
