package dataAccessTests;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    void clearData() throws DataAccessException {
        userDAO.addUser(new UserData("useGuy", "password", "email"));
        userDAO.addUser(new UserData("usedude", "password", "email"));
        userDAO.addUser(new UserData("userman", "password", "email"));
        userDAO.addUser(new UserData("userGal", "password", "email"));
        userDAO.addUser(new UserData("userGee", "password", "email"));

        List<UserData> users = new ArrayList<>();
        users.add(userDAO.getUser("useGuy"));
        users.add(userDAO.getUser("usedude"));
        users.add(userDAO.getUser("userman"));
        users.add(userDAO.getUser("useGal"));
        users.add(userDAO.getUser("useGee"));


        userDAO.clearData();
        UserData userConfirm;

        for (UserData user : users) {
            userConfirm = userDAO.getUser(user.username());
            assertNull(userConfirm.username());
            assertNull(userConfirm.password());
            assertNull(userConfirm.email());
        }
    }
}
