package dataAccess;

import model.AuthData;

public class SQLAuthDAO implements AuthDAO {

    @Override
    public AuthData getAuth(String username) {
        return null;
    }

    @Override
    public AuthData getAuthFromToken(String authString) {
        return null;
    }

    @Override
    public boolean validateAuth(AuthData auth) {
        return false;
    }

    @Override
    public AuthData createAuth(String username) {
        return null;
    }

    @Override
    public AuthData addAuth(AuthData auth) {
        return null;
    }

    @Override
    public boolean removeAuth(AuthData auth) {
        return false;
    }

    @Override
    public String generateAuth() {
        return null;
    }

    @Override
    public boolean clearData() {
        return false;
    }
}
