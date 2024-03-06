package dataAccess;

import com.mysql.cj.exceptions.DataReadException;
import model.UserData;

import java.util.List;

public interface UserDAO {

    void addUser(UserData user) throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;

    boolean clearData() throws DataAccessException;
}
