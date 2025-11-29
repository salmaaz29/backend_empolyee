package ma.fstt.backend_empolyee.services;

import lombok.RequiredArgsConstructor;
import ma.fstt.backend_empolyee.entities.Employee;
import ma.fstt.backend_empolyee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class EmployeeServiceEmpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id){
         employeeRepository.deleteById(id);
    }

    @Override
    public Employee update(Long id ,Employee employee) {
        Employee emp = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("employee non trouve de cet id : " + id));
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setEmail(employee.getEmail());
        emp.setSalary(employee.getSalary());
        return employeeRepository.save(emp);
    }
}
