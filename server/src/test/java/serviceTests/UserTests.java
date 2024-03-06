package serviceTests;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.memory.MemoryAuthDAO;
import dataAccess.memory.MemoryUserDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    void getUser() throws DataAccessException {
        UserDAO memDAO = new MemoryUserDAO();
        UserData u = new UserData("WatcherMagic", "apassword", "mailll");
        memDAO.addUser(u);

        assertEquals(memDAO.getUser("WatcherMagic"), u);
        assertEquals(memDAO.getUser("Bob"), null);
    }

    @Test
    void containsUser() throws DataAccessException {
        UserDAO memDAO = new MemoryUserDAO();
        UserData u = new UserData("WatcherMagic", "password", "mail");
        memDAO.addUser(u);

        assertNotEquals(memDAO.containsUser("WatcherMagic"), -1);
        assertEquals(memDAO.containsUser("Joe"), -1);
    }

    @Test
    void getAuth() throws DataAccessException {
        AuthData auth = new AuthData("WatcherMagic", "123");
        AuthDAO autHDAO = new MemoryAuthDAO();
        autHDAO.addAuth(auth);

        assertEquals(autHDAO.getAuth("WatcherMagic"), auth);
    }

    @Test
    void addNewUser() throws DataAccessException {
        UserDAO user = new MemoryUserDAO();
        AuthDAO auth = new MemoryAuthDAO();
        UserService register = new UserService(user, auth);
        register.register(new UserData("WatcherMagic", "superawesomepassword", "spam"));

        assertNotEquals(user.containsUser("WatcherMagic"), -1);
        assertNotEquals(auth.getAuth("WatcherMagic"), null);
    }

    @Test
    public void authRefreshedOnSuccessfulLogin() throws DataAccessException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        UserService userService = new UserService(userDAO, authDAO);

        UserData user = new UserData("WatcherMagic", "imafortress", "mail");
        userService.register(user);
        AuthData oldToken = authDAO.getAuth("WatcherMagic");

        userService.logout(authDAO.getAuth(user.username()));
        userService.login(user);
        AuthData newToken = authDAO.getAuth("WatcherMagic");

        assertNotEquals(oldToken, newToken);
    }

    @Test
    public void authRefreshFailOnUnsuccessfulLogin() throws DataAccessException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        UserService userService = new UserService(userDAO, authDAO);

        userService.register(new UserData("WatcherMagic", "imafortress", "spam"));
        AuthData oldToken = authDAO.getAuth("WatcherMagic");

        UserData user = new UserData("WatcherMagic", "wrongpassword", null);
        userService.login(user);
        AuthData newToken = authDAO.getAuth("WatcherMagic");

        assertEquals(oldToken, newToken);
    }

    @Test
    public void logout() throws DataAccessException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        UserService userService = new UserService(userDAO, authDAO);

        userService.register(new UserData("WatcherMagic", "imafortress", "spam"));
        AuthData token = authDAO.getAuth("WatcherMagic");

        userService.logout(token);
        assertEquals(authDAO.getAuth(token.username()), null);
    }
}