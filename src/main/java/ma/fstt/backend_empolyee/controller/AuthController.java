package ma.fstt.backend_empolyee.controller;

import ma.fstt.backend_empolyee.entities.Employee;
import ma.fstt.backend_empolyee.security.AuthRequest;
import ma.fstt.backend_empolyee.security.AuthResponse;
import ma.fstt.backend_empolyee.security.JwtUtil;
import ma.fstt.backend_empolyee.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

    /**
     * Endpoint pour s'inscrire (créer un nouveau compte)
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Employee employee) {
        try {
            // Vérifier si l'email existe déjà
            if (employeeService.existsByEmail(employee.getEmail())) {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"Email déjà utilisé\", \"error\": \"EMAIL_EXISTS\"}");
            }

            // Vérifier que le mot de passe n'est pas vide
            if (employee.getPassword() == null || employee.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"Le mot de passe est requis\", \"error\": \"PASSWORD_REQUIRED\"}");
            }

            // Sauvegarder l'employé (le mot de passe sera encodé automatiquement dans le service)
            Employee savedEmployee = employeeService.save(employee);

            return ResponseEntity.ok()
                    .body("{\"message\": \"Compte créé avec succès\", \"email\": \"" + savedEmployee.getEmail() + "\"}");

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Erreur lors de la création du compte: " + e.getMessage() + "\", \"error\": \"CREATION_ERROR\"}");
        }
    }

    /**
     * Endpoint pour vérifier si un token est valide (optionnel)
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("Token manquant ou invalide");
            }

            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            boolean isValid = jwtUtil.validateToken(token, userDetails);

            if (isValid) {
                return ResponseEntity.ok().body("{\"valid\": true, \"username\": \"" + username + "\"}");
            } else {
                return ResponseEntity.badRequest().body("{\"valid\": false}");
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"valid\": false, \"error\": \"Token invalide\"}");
        }
    }
}