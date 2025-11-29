// src/main/java/ma/fstt/backend_employee/security/services/CustomUserDetailsService.java
package ma.fstt.backend_empolyee.services;



import ma.fstt.backend_empolyee.entities.Employee;
import ma.fstt.backend_empolyee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("🔍 Recherche de l'utilisateur: " + email); // ← AJOUTEZ CETTE LIGNE

        Employee employee = employeeRepository.findByEmail(email);

        if (employee == null) {
            System.out.println("❌ Utilisateur non trouvé: " + email); // ← AJOUTEZ CETTE LIGNE
            throw new UsernameNotFoundException("Employé non trouvé: " + email);
        }

        System.out.println("✅ Utilisateur trouvé: " + employee.getEmail()); // ← AJOUTEZ CETTE LIGNE
        System.out.println("🔐 Password dans la base: " + employee.getPassword()); // ← AJOUTEZ CETTE LIGNE

        return employee;
    }
}