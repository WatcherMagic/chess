package service;

public class LoginAndRegisterResponse extends Response {
    String authToken;
    String username;

    public LoginAndRegisterResponse(String message, String username, String authtoken) {
        super(message);
        this.authToken = authtoken;
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }
}
