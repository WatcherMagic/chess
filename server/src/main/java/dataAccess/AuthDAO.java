package dataAccess;

import model.AuthToken;

public interface AuthDAO {

    public AuthToken getAuth(String username);

    public AuthToken getAuthFromToken(String authString);

    public boolean validateAuth(AuthToken auth);

    public AuthToken createAuth(String username);

    public AuthToken addAuth(AuthToken auth);

    public void removeAuth(AuthToken auth);

    public String generateAuth();

    void clearData();
}
