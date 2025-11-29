package ma.fstt.backend_empolyee.repository;

import ma.fstt.backend_empolyee.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);
    // Vérifier si un email existe déjà
    boolean existsByEmail(String email);
}
