package dataAccess;

import model.AuthData;
import model.UserData;

import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {
    @Override
    public void addUser(UserData user) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO user_data (username, password, email) VALUES (?, ?, ?)")) {
                preparedStatement.setString(1, user.username());
                preparedStatement.setString(2, user.password());
                preparedStatement.setString(3, user.email());
                preparedStatement.executeUpdate();}
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM user_data WHERE username=?")) {
                String name = null;
                String password = null;
                String email = null;
                preparedStatement.setString(1, username);
                var rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    name = rs.getString("username");
                    password = rs.getString("password");
                    email = rs.getString("email");
                }
                return new UserData(name, password, email);
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public boolean clearData() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE user_data")) {
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
}
