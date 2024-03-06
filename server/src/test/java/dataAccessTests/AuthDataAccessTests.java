package dataAccessTests;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.SQLAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthDataAccessTests {
    AuthDAO authDAO;

    @BeforeEach
    void setUpDAOs() {
        authDAO = new SQLAuthDAO();
    }

    @Test
    void getAuthFromUsernameSuccess() throws DataAccessException  {
        AuthData newAuth = authDAO.createAuth("CoolName");
        AuthData authConfirm = authDAO.addAuth(newAuth);
        assertNotNull(authConfirm);

        authConfirm = authDAO.getAuth(newAuth.username());
        assertNotNull(authConfirm);
        assertEquals(authConfirm, newAuth);
    }

    @Test
    void getAuthFromTokenSuccess() {

    }

    @Test
    void getAuthFromUsernameFail() {

    }

    @Test
    void getAuthFromTokenFail() {

    }

    @Test
    void removeAuthSuccess() {

    }

    @Test
    void removeAuthFail() {

    }

    @Test
    void clearAuthData() {

    }
}
