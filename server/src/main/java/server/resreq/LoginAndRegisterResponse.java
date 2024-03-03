package server.resreq;

import dataAccess.objects.AuthToken;

public class LoginAndRegisterResponse extends Response {
    String authToken;
    String username;

    public LoginAndRegisterResponse(String message, String username, String authtoken) {
        super(message);
        this.authToken = authtoken;
        this.username = username;
    }

    public boolean userInfoNull() {
        if (authToken == null && username == null) {
            return true;
        }
        return false;
    }
}
