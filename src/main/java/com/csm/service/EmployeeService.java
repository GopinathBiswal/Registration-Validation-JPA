package com.csm.service;

import java.util.List;

import com.csm.entity.Employee;

public interface EmployeeService {
	
	public String upsertEmployee(Employee emp);
	
	public Employee getEmpById(Integer eid);
	
	public List<Employee> getAllEmployees();
	
	public String deleteEmpById(Integer eid);

}
