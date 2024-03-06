package dataAccess;

import model.AuthData;

import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO {

    @Override
    public AuthData getAuth(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM auth_data WHERE username=?")) {
                String name = null;
                String token = null;

                preparedStatement.setString(1, username);
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
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM auth_data WHERE token =?")) {
                String username = null;
                String token = null;

                preparedStatement.setString(1, authString);
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
        String newUsername = auth.username();
        String newToken = auth.token();
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO auth_data (username, token) VALUES (?, ?)")) {
                preparedStatement.setString(1, newUsername);
                preparedStatement.setString(2, newToken);
                preparedStatement.executeUpdate();
                return new AuthData(newUsername, newToken);
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public boolean removeAuth(AuthData auth) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM auth_data WHERE username=?")) {
                preparedStatement.setString(1, auth.username());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public String generateAuth() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean clearData() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE auth_data")) {
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
}
