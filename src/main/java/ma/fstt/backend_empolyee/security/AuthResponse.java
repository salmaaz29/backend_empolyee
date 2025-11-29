package ma.fstt.backend_empolyee.security;

public class AuthResponse {
    private String token;


    public AuthResponse(String token) {
        this.token = token;
    }

    // getters et setters

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
