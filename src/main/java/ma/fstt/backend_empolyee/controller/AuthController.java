package ma.fstt.backend_empolyee.controller;

import ma.fstt.backend_empolyee.entities.Employee;
import ma.fstt.backend_empolyee.security.AuthRequest;
import ma.fstt.backend_empolyee.security.AuthResponse;
import ma.fstt.backend_empolyee.security.JwtUtil;
import ma.fstt.backend_empolyee.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmployeeService employeeService;

    /**
     * Endpoint pour se connecter
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        System.out.println("🔐 LOGIN MANUEL START =========================");

        try {
            // 1. Chercher l'utilisateur MANUELLEMENT
            System.out.println("🔍 Recherche manuelle de: " + authRequest.getUsername());
            Employee employee = employeeService.findByEmail(authRequest.getUsername());

            if (employee == null) {
                System.out.println("❌ Utilisateur non trouvé");
                return ResponseEntity.badRequest().body("Email ou mot de passe incorrect");
            }

            System.out.println("✅ Utilisateur trouvé: " + employee.getEmail());
            System.out.println("🔐 Password en base: " + employee.getPassword());
            System.out.println("🔑 Password fourni: " + authRequest.getPassword());

            // 2. Comparer les passwords MANUELLEMENT
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean passwordMatches = passwordEncoder.matches(
                    authRequest.getPassword(),
                    employee.getPassword()
            );

            System.out.println("🎯 PasswordEncoder.matches(): " + passwordMatches);

            if (!passwordMatches) {
                System.out.println("❌ Password ne correspond pas");
                return ResponseEntity.badRequest().body("Email ou mot de passe incorrect");
            }

            System.out.println("✅ Password validé avec succès!");

            // 3. Générer le token MANUELLEMENT
            final String jwt = jwtUtil.generateToken(authRequest.getUsername());
            System.out.println("🎫 Token généré: " + jwt);

            System.out.println("🔐 LOGIN MANUEL SUCCÈS =====================");
            return ResponseEntity.ok(new AuthResponse(jwt));

        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erreur d'authentification");
        }
    }
}