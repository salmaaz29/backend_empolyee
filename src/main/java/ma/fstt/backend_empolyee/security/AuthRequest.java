package ma.fstt.backend_empolyee.security;

public class AuthRequest {
    private String email;
    private String password;


    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;

    }
    // getters et setters

    public String getUsername() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String email) {
        this.email = email;
    }

}
