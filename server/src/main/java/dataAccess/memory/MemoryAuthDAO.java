package dataAccess.memory;

import dataAccess.AuthDAO;
import model.AuthData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

    List<AuthData> tokens;

    public MemoryAuthDAO() {
        tokens = new ArrayList<>();
    }

    @Override
    public AuthData getAuth(String username) {
        for (AuthData token : tokens) {
            if (token.username().equals(username)) {
                return token;
            }
        }
        return null;
    }

    @Override
    public AuthData getAuthFromToken(String authString) {
        for (AuthData token : tokens) {
            if (Objects.equals(token.token(), authString)) {
                return token;
            }
        }
        return null;
    }

    @Override
    public boolean validateAuth(AuthData auth) {
        if (auth != null && getAuthFromToken(auth.token()) == auth) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public AuthData createAuth(String username) {
        return new AuthData(username, generateAuth());
    }

    @Override
    public AuthData addAuth(AuthData auth) {
        tokens.add(auth);
        return auth;
    }

    @Override
    public boolean removeAuth(AuthData auth) {
        tokens.remove(tokens.indexOf(auth));
        if (tokens.indexOf(auth) == -1) {
            return true;
        }
        return false;
    }

    @Override
    public String generateAuth() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean clearData() {
        tokens.clear();
        if (tokens.size() == 0) {
            return true;
        }
        return false;
    }
}
