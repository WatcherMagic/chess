package dataAccess;

import model.AuthToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MemAuthDAO implements AuthDAO {

    List<AuthToken> tokens;

    public MemAuthDAO() {
        tokens = new ArrayList<>();
    }

    @Override
    public AuthToken getAuth(String username) {
        for (AuthToken token : tokens) {
            if (token.username().equals(username)) {
                return token;
            }
        }
        return null;
    }

    @Override
    public AuthToken getAuthFromToken(String authString) {
        for (AuthToken token : tokens) {
            if (Objects.equals(token.token(), authString)) {
                return token;
            }
        }
        return null;
    }

    @Override
    public boolean validateAuth(AuthToken auth) {
        if (auth != null && getAuthFromToken(auth.token()) == auth) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public AuthToken createAuth(String username) {
        return new AuthToken(username, generateAuth());
    }

    @Override
    public AuthToken addAuth(AuthToken auth) {
        tokens.add(auth);
        return auth;
    }

    @Override
    public void removeAuth(AuthToken auth) {
        tokens.remove(tokens.indexOf(auth));
    }

    @Override
    public String generateAuth() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void clearData() {
        tokens.clear();
    }
}
