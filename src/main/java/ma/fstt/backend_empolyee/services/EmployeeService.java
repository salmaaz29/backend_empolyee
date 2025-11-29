package ma.fstt.backend_empolyee.services;

import ma.fstt.backend_empolyee.entities.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee save(Employee employee);
    Employee findById(Long id);
    void delete(Long id);
    Employee update(Long id , Employee employee);
    // Ajouter ces méthodes
    Employee findByEmail(String email);
    boolean existsByEmail(String email);

}
