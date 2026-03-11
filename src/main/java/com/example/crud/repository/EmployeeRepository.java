package com.example.crud.repository;

import com.example.crud.model.Employee;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT COUNT(*) FROM employees", nativeQuery = true)
    long totalEmployees();

    @Query(value = "SELECT AVG(salary) FROM employees", nativeQuery = true)
    Double avgSalary();

    @Query(value = "SELECT department, COUNT(*) FROM employees GROUP BY department", nativeQuery = true)
    List<Object[]> deptStats();

    @Query(value = "SELECT * FROM employees WHERE salary > :amount", nativeQuery = true)
    List<Employee> highEarners(@Param("amount") double amount);
	
	@Query(value="SELECT SUM(salary) FROM employees", nativeQuery=true)
	Double totalPayroll();

}
