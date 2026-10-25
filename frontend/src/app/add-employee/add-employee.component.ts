
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Employee, EmployeeService } from '../services/employee.service';



@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent {

  // Holds the values entered in the form.
  employee: Employee = {
    name: '',
    email: '',
    department: '',
    position: '',
    status: 'Active'
  };

  // Controls the Save button while the API request runs.
  isSaving = false;

  // Displays success or error messages.
  successMessage = '';
  errorMessage = '';

  // Injects the API service and Angular router.
  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  // Validates and submits the employee form.
  saveEmployee(form: NgForm): void {

    // Stops submission when required fields are invalid.
    if (form.invalid) {
      form.control.markAllAsTouched();
      return;
    }

    this.successMessage = '';
    this.errorMessage = '';
    this.isSaving = true;

    // Sends employee data to the Spring Boot backend.
    this.employeeService.create(this.employee).subscribe({

      // Runs when the backend successfully saves the employee.
      next: () => {
        this.isSaving = false;
        this.successMessage = 'Employee added successfully!';

        // Clears the form and restores the default status.
        form.resetForm({
          name: '',
          email: '',
          department: '',
          position: '',
          status: 'Active'
        });

        // Returns to the employee list after one second.
        setTimeout(() => {
          this.router.navigate(['/employees']);
        }, 1000);
      },

      // Runs when the backend returns an error.
      error: (error) => {
        console.error('Error saving employee:', error);

        this.errorMessage =
          error?.error?.message ||
          'Unable to save employee. Please check the backend.';

        this.isSaving = false;
      }
    });
  }

  // Returns to the employee list without saving.
  cancel(): void {
    this.router.navigate(['/employees']);
  }
}
