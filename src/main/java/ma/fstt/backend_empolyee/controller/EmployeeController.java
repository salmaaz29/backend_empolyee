package ma.fstt.backend_empolyee.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.fstt.backend_empolyee.dto.EmployeeDTO;
import ma.fstt.backend_empolyee.entities.Employee;
import ma.fstt.backend_empolyee.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDTO> findAll() {

        return employeeService.findAll().stream()
                .map(emp -> new EmployeeDTO(
                        emp.getId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getEmail(),
                        emp.getSalary()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public Employee save(@Valid @RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id,@Valid @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }

}
