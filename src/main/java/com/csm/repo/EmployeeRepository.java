package com.csm.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csm.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Serializable>{

	boolean existsByMail(String mail);

}
