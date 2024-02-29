package dataAccess;

import dataAccess.objects.AuthToken;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemAuthDAO implements AuthDAO {

    List<AuthToken> tokens;

    public MemAuthDAO() {
        tokens = new ArrayList<>();
    }

    @Override
    public AuthToken getAuth(String username) {
        for (AuthToken token : tokens) {
            if (token.username() == username) {
                return token;
            }
        }
        return null;
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
    public void replaceAuth(String username) {
        AuthToken update = getAuth(username);
        int index = tokens.indexOf(update);
        tokens.set(index, new AuthToken(username, generateAuth()));
    }

    @Override
    public void removeAuth(AuthToken auth) {
        tokens.remove(tokens.indexOf(auth));
    }

    @Override
    public String generateAuth() {
        return UUID.randomUUID().toString();
    }
}
