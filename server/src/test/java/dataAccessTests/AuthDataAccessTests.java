package dataAccessTests;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.SQLAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Server;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthDataAccessTests {
    AuthDAO authDAO = new SQLAuthDAO();

    @BeforeEach
    void setUp() throws DataAccessException {
        authDAO.clearData();
    }

    @Test
    void getAuthFromUsernameSuccess() throws DataAccessException  {
        AuthData newAuth = authDAO.createAuth("CoolUser");
        AuthData authConfirm = authDAO.addAuth(newAuth);
        assertNotNull(authConfirm);

        authConfirm = authDAO.getAuth(newAuth.username());
        assertNotNull(authConfirm);
        assertEquals(authConfirm, newAuth);
    }

    @Test
    void getAuthFromTokenSuccess() throws DataAccessException {
        AuthData newAuth = authDAO.createAuth("CoolUser");
        AuthData authConfirm = authDAO.addAuth(newAuth);
        assertNotNull(authConfirm);

        authConfirm = authDAO.getAuthFromToken(newAuth.token());
        assertNotNull(authConfirm);
        assertEquals(authConfirm, newAuth);
    }

    @Test
    void getAuthFromUsernameFail() throws DataAccessException {
        AuthData newAuth = authDAO.createAuth("CoolUser");
        AuthData authConfirm = authDAO.getAuth(newAuth.username());
        assertNotNull(authConfirm);
        assertNull(authConfirm.username());
        assertNull(authConfirm.token());
    }

    @Test
    void getAuthFromTokenFail() throws DataAccessException {
        AuthData newAuth = authDAO.createAuth("CoolUser");
        AuthData authConfirm = authDAO.getAuthFromToken(newAuth.token());
        assertNotNull(authConfirm);
        assertNull(authConfirm.username());
        assertNull(authConfirm.token());
    }

    @Test
    void removeAuthSuccess() throws DataAccessException {
        AuthData newAuth = authDAO.createAuth("CoolUser");
        AuthData authConfirm = authDAO.addAuth(newAuth);
        assertNotNull(authConfirm);

        authConfirm = authDAO.getAuth(newAuth.username());
        assertEquals(authConfirm, newAuth);

        authDAO.removeAuth(newAuth);
        authConfirm = authDAO.getAuth(newAuth.username());
        assertNull(authConfirm.username());
        assertNull(authConfirm.token());
    }

    @Test
    void removeAuthFail() throws DataAccessException {
        AuthData newAuth = authDAO.createAuth("CoolUser");
        AuthData authConfirm = authDAO.addAuth(newAuth);
        assertNotNull(authConfirm);

        authConfirm = authDAO.getAuth(newAuth.username());
        assertEquals(authConfirm, newAuth);

        authDAO.removeAuth(new AuthData("wrongUsername", "wrongToken"));
        authConfirm = authDAO.getAuth(newAuth.username());
        assertEquals(authConfirm, newAuth);
    }

    @Test
    void clearAuthData() throws DataAccessException {
        List<AuthData> userAuths = new ArrayList<>();
        userAuths.add(authDAO.createAuth("CoolName"));
        userAuths.add(authDAO.createAuth("ActiveUser"));
        userAuths.add(authDAO.createAuth("GoodPlayer"));
        userAuths.add(authDAO.createAuth("ThatOneGuy"));
        userAuths.add(authDAO.createAuth("InvisibleObserver"));

        authDAO.clearData();
        AuthData authConfirm;

        for (AuthData auth : userAuths) {
            authConfirm = authDAO.getAuth(auth.username());
            assertNull(authConfirm.username());
            assertNull(authConfirm.token());
        }
    }
}
