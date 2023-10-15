package com.csm.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csm.entity.Employee;
import com.csm.repo.EmployeeRepository;
import com.csm.service.EmployeeService;

@RestController
public class EmployeeRestController {

	@Autowired
	private EmployeeService empSrvc;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
		try {
			if (employee.getName() == null || employee.getName().isEmpty()) {
				return new ResponseEntity<>("Name cannot be blank", HttpStatus.BAD_REQUEST);
			}
			
			if (employee.getMail() == null || employee.getMail().isEmpty()) {
				return new ResponseEntity<>("Email cannot be blank", HttpStatus.BAD_REQUEST);
			}
			
			// Check if the email already exists in the database
			if (empRepo.existsByMail(employee.getMail())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email address is already registered");
			}

			// Save the new employee to the database
			String savedEmp = empSrvc.upsertEmployee(employee);

			return ResponseEntity.ok(savedEmp);
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while adding the employee");
		}
	}
	
//	@PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<String> saveEmployee(@RequestBody Employee emp) {
//		String savedEmployee = empSrvc.upsertEmployee(emp);
//		return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
//	}
	
	@GetMapping(value = "/emp/{eid}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer eid) {
		Employee employee = empSrvc.getEmpById(eid);
		
		if (employee != null) {
			return new ResponseEntity<>(employee, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping(value = "/emps")
	public ResponseEntity<List<Employee>> retriveAllEmps() {
		List<Employee> employees = empSrvc.getAllEmployees();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
	
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateEmployee(@RequestBody Employee emp) {
		empSrvc.upsertEmployee(emp);
		return ResponseEntity.status(HttpStatus.OK).body("Employee Record Updated");
	}
	
	@DeleteMapping(value = "/delete/{eid}")
	public ResponseEntity<String> removeEmpById(@PathVariable Integer eid){
		String deleteEmpById = empSrvc.deleteEmpById(eid);
		return new ResponseEntity<>(deleteEmpById, HttpStatus.OK);
	}
	
}
