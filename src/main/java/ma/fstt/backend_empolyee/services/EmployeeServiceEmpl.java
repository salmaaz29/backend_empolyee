package ma.fstt.backend_empolyee.services;

import lombok.RequiredArgsConstructor;
import ma.fstt.backend_empolyee.entities.Employee;
import ma.fstt.backend_empolyee.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class EmployeeServiceEmpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee save(Employee employee) {
        // encoder password avant save
        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
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

        // encoder password avant de sauvgarder modification
        if(emp.getPassword() != null && !emp.getPassword().isEmpty()){
            String encodedPassword = passwordEncoder.encode(emp.getPassword());
            emp.setPassword(encodedPassword);
        }
        return employeeRepository.save(emp);
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }
}
