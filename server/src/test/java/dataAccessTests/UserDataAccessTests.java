package dataAccessTests;

import dataAccess.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserDataAccessTests {

    UserDAO userDAO = new SQLUserDAO();

    @BeforeEach
    void setUp() throws DataAccessException {
        userDAO.clearData();
    }

    @Test
    void addAndGetUserSuccess() throws DataAccessException {
        UserData user = new UserData("Cool Guy", "impregnablePassword", "email");
        userDAO.addUser(user);

        UserData confirmUser = userDAO.getUser(user.username());
        assertEquals(confirmUser, user);
    }

    @Test
    void getUserFail() throws DataAccessException {
        UserData user = new UserData("Cool Guy", "impregnablePassword", "email");
        userDAO.addUser(user);

        UserData confirmUser = userDAO.getUser("wrongUsername");
        assertNotEquals(confirmUser, user);
    }
}
