package dataAccess;

import com.mysql.cj.exceptions.DataReadException;
import model.UserData;

import java.util.List;

public interface UserDAO {

    void addUser(UserData user) throws DataAccessException;

    String hashPassword(String password);

    boolean verifyPassword(String username, String password) throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;

    boolean clearData() throws DataAccessException;
}
