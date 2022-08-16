package com.restapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.exception.ResourceNotFoundException;
import com.restapi.modal.Employee;
import com.restapi.repository.EmployeeRepositry;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/api")
public class EmployeeController {
	@Autowired
	private EmployeeRepositry employeeRepositry;
	
	@GetMapping("/employee")
	public List <Employee> getAllEmployee(){
		return employeeRepositry.findAll();
	}
    @PostMapping("/employee")	
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepositry.save(employee);
	}
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
    	Employee employee=employeeRepositry.findById(id)
    			.orElseThrow(()-> new ResourceNotFoundException("Employee id not exists" + id));
    	return ResponseEntity.ok(employee);
    }
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> editEmployeeData(@PathVariable long id, @RequestBody Employee employeeDetail){
    	Employee employee = employeeRepositry.findById(id)
    			.orElseThrow(()->new ResourceNotFoundException("Employee id not exists" + id));
    	employee.setFirstName(employeeDetail.getFirstName());
    	employee.setLastName(employeeDetail.getLastName());
    	employee.setEmail(employeeDetail.getEmail());
    	employee.setSalary(employeeDetail.getSalary());
    	
    	Employee updatetedData = employeeRepositry.save(employee);
    	return ResponseEntity.ok(updatetedData);
    	
    }
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable long id){
    	Employee employee = employeeRepositry.findById(id)
    			.orElseThrow(()->new ResourceNotFoundException("Employee id not exists" + id));
    	employeeRepositry.delete(employee);
    	Map<String , Boolean> response = new HashMap<>();
    	response.put("deleted", Boolean.TRUE);
    	return ResponseEntity.ok(response);
    }

}
