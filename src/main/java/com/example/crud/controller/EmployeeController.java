package com.example.crud.controller;

import com.example.crud.model.Employee;
import com.example.crud.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin
public class EmployeeController {

    private final EmployeeRepository repo;

    public EmployeeController(EmployeeRepository repo){
        this.repo = repo;
    }

    @GetMapping
    public List<Employee> all(){
        return repo.findAll();
    }

    @PostMapping
    public Employee add(@RequestBody Employee e){
        return repo.save(e);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id,@RequestBody Employee e){
        e.setId(id);
        return repo.save(e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        repo.deleteById(id);
    }

    // ===== SQL ANALYTICS ENDPOINTS =====

    @GetMapping("/stats")
	public Map<String,Object> stats(){
		Map<String,Object> m=new HashMap<>();
		m.put("total", repo.totalEmployees());
		m.put("avgSalary", repo.avgSalary());
		m.put("payroll", repo.totalPayroll());
		return m;
	}


    @GetMapping("/departments")
    public List<Map<String,Object>> departments(){
        List<Map<String,Object>> list = new ArrayList<>();
        for(Object[] r : repo.deptStats()){
            Map<String,Object> m = new HashMap<>();
            m.put("department", r[0]);
            m.put("count", r[1]);
            list.add(m);
        }
        return list;
    }

    @GetMapping("/high/{amount}")
    public List<Employee> high(@PathVariable double amount){
        return repo.highEarners(amount);
    }
}
