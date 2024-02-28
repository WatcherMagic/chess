package dataAccess;

import dataAccess.objects.AuthToken;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemAuthDAO implements AuthDAO {

    //list of auths
    List<AuthToken> tokens;

    //make sure you don't create a new instance of this class every time it's called!
    //otherwise user data will go poof
    public MemAuthDAO() {
        tokens = new ArrayList<>();
    }

    @Override
    public AuthToken getAuth(String username) {
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

    }

    @Override
    public String generateAuth() {
        return UUID.randomUUID().toString();
    }
}
