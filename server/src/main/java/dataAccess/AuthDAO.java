package dataAccess;

import model.AuthData;

import java.util.List;

public interface AuthDAO {

    AuthData getAuth(String username);

    AuthData getAuthFromToken(String authString);

    boolean validateAuth(AuthData auth);

    AuthData createAuth(String username);

    AuthData addAuth(AuthData auth);

    boolean removeAuth(AuthData auth);

    String generateAuth();

    boolean clearData();
}
