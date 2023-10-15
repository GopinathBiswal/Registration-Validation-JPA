package com.csm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csm.entity.Employee;
import com.csm.repo.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository empRepo;
	
	/**
	 * Upsert means it can update or insert both
	 * 
	 * @author gopinath.biswal
	 * 
	 * @param emp
	 * @return success message
	 */
	@Override
	public String upsertEmployee(Employee emp) {
		empRepo.save(emp);
		return "Employee Added Successfully.";
	}
	
	@Override
	public Employee getEmpById(Integer eid) {
		Optional<Employee> findById = empRepo.findById(eid);
		
		if (findById.isPresent()) {
			return findById.get();
		} else {
			System.out.println("No employee record available for id: " + eid);
			return null;
		}
	}
	
	@Override
	public List<Employee> getAllEmployees(){
		List<Employee> emps = empRepo.findAll();
		return emps;
	}
	
	@Override
	public String deleteEmpById(Integer eid) {
		if (empRepo.existsById(eid)) {
			empRepo.deleteById(eid);
			return "Employee Deleted Successfully";
		} else {
			return "No Record Found.";
		}
	}
}
