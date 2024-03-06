package dataAccess;

import model.AuthData;

import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO {

    @Override
    public AuthData getAuth(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT token, username FROM auth_data WHERE token=?")) {
                String name = null;
                String token = null;
                var rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    name = rs.getString("username");
                    token = rs.getString("token");
                }
                return new AuthData(name, token);
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public AuthData getAuthFromToken(String authString) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT token, username FROM auth_data WHERE token=?")) {
                String username = null;
                String token = null;
                var rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    username = rs.getString("username");
                    token = rs.getString("token");
                }
                return new AuthData(username, token);
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public boolean validateAuth(AuthData auth) throws DataAccessException {
        if (auth != null && getAuthFromToken(auth.token()) == auth) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        return new AuthData(username, generateAuth());
    }

    @Override
    public AuthData addAuth(AuthData auth) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO auth_data")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
                return new AuthData("username", "token");
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public boolean removeAuth(AuthData auth) throws DataAccessException {
        return false;
    }

    @Override
    public String generateAuth() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean clearData() throws DataAccessException {
        return false;
    }
}
