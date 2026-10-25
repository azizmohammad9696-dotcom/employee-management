
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Defines the structure of an employee.
export interface Employee {
  id?: number;
  name: string;
  email: string;
  department: string;
  position: string;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  // Spring Boot REST API base URL.
  private apiUrl = 'http://localhost:8080/api/employees';

  // Injects HttpClient to communicate with the backend.
  constructor(private http: HttpClient) {}

  // Fetches all employees from the database.
  getAll(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.apiUrl);
  }

  // Fetches one employee by ID.
  getEmployeeById(id: number): Observable<Employee> {
    return this.http.get<Employee>(`${this.apiUrl}/${id}`);
  }

  // Saves a new employee in the database.
  create(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.apiUrl, employee);
  }

  // Updates an existing employee.
  updateEmployee(
    id: number,
    employee: Employee
  ): Observable<Employee> {
    return this.http.put<Employee>(
      `${this.apiUrl}/${id}`,
      employee
    );
  }

  // Deletes an employee by ID.
  deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
