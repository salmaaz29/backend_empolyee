# Gestion des employees: 
## Objectif
Application backend REST API pour la gestion des employés avec authentification JWT, développée avec Spring Boot 3, MySQL et Spring Security.
 Fonctionnalités

✅ CRUD complet sur les employés
✅ Authentification JWT sécurisée
✅ Validation des données avec Jakarta Validation
✅ Cryptage des mots de passe avec BCrypt
✅ Configuration CORS pour frontend Angular
✅ Gestion des erreurs

## Technologies utilisées
| Technologie | Version | Description |
|-------------|---------|-------------|
| Java | 17+ | Langage de programmation |
| Spring Boot | 3.x | Framework backend |
| Spring Data JPA | 3.x | ORM et accès aux données |
| Spring Security | 7.x | Authentification et autorisation |
| MySQL | 5.5+ | Base de données relationnelle |
| Lombok | 1.18+ | Réduction du code boilerplate |
| JJWT | 0.12.6 | Gestion des tokens JWT |
| Jakarta Validation | 3.x | Validation des données |
| Maven | 3.8+ | Gestionnaire de dépendances |

## Structure du projet
 ```
src/main/java/ma/fstt/backend_employee/
├── SecurityConfig.java                 # Configuration Spring Security       
├── controller/
│   ├── AuthController.java              # API Authentification
│   ├── EmployeeController.java          # API CRUD Employés
├── entities/
│   └── Employee.java                    # Entité JPA
├── repository/
│   └── EmployeeRepository.java          # Repository JPA
├── services/
│   ├── EmployeeService.java             # Interface Service
│   ├── EmployeeServiceEmpl.java         # Implémentation Service
│   ├── CustomUserDetailsService.java    # UserDetailsService
│   └── ReportService.java               # Service PDF
├── security/
│   ├── JwtUtil.java                     # Utilitaire JWT
│   ├── JwtAuthenticationFilter.java     # Filtre JWT
│   ├── AuthRequest.java                 # Login Request
│   └── AuthResponse.java                # Login Response
├── dto/
│   └── EmployeeDTO.java                 # DTO Employé
└── BackendEmployeeApplication.java      # Classe principale
 ```
## Prérequis
- Java 17 ou supérieur
- Maven 3.8+
- MySQL 8.0

## Installation & lancement

1. **Cloner le projet**
   ```
   git clone https://github.com/salmaaz29/backend_empolyee.git
   cd backend_empolyee
    ```
2. **Configurer MySQLSQL**
   ```
   CREATE DATABASE gestion_employee;
   ```
3. **Configurer application.properties**
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/gestion_employee
   spring.datasource.username=root
   spring.datasource.password=votre_mot_de_passe
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
   server.port=8080
4. **installer les dépendances**
  ```
mvn clean install   L'application sera accessible sur http://localhost:8080
  ```
5. **Sécurité**
  ```
JWT (JSON Web Token)
Durée de validité : 24 heures
Header : Authorization: Bearer {token}
  ```
6. **Test**
  ```
Avec Postman
Tester le login :
- Endpoint : POST http://localhost:8080/api/auth/login
- Body : {"email": "test@test.com", "password": "password"}
- Copier le token reçu
- Tester les endpoints protégés en ajoutant le header :Authorization: Bearer {votre_token}
  ```
