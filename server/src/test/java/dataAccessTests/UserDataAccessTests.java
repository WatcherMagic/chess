package dataAccessTests;

import dataAccess.AuthDAO;
import dataAccess.SQLAuthDAO;
import dataAccess.SQLUserDAO;
import dataAccess.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDataAccessTests {

    UserDAO userDAO;
    AuthDAO authDAO;

    @BeforeEach
    void setUpDAOs() {
        userDAO = new SQLUserDAO();
        authDAO = new SQLAuthDAO();
    }

    @Test
    void name() {
    }
}
