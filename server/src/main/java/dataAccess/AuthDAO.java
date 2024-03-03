package dataAccess;

import model.AuthData;

public interface AuthDAO {

    public AuthData getAuth(String username);

    public AuthData getAuthFromToken(String authString);

    public boolean validateAuth(AuthData auth);

    public AuthData createAuth(String username);

    public AuthData addAuth(AuthData auth);

    public void removeAuth(AuthData auth);

    public String generateAuth();

    void clearData();
}
