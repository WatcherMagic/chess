package dataAccess;

import model.AuthData;

import java.util.List;

public interface AuthDAO {

    AuthData getAuth(String username) throws DataAccessException;

    AuthData getAuthFromToken(String authString) throws DataAccessException;

    boolean validateAuth(AuthData auth) throws DataAccessException;

    AuthData createAuth(String username) throws DataAccessException;

    AuthData addAuth(AuthData auth) throws DataAccessException;

    boolean removeAuth(AuthData auth) throws DataAccessException;

    String generateAuth();

    boolean clearData() throws DataAccessException;
}
