package passoffTests.serverTests;

import dataAccess.AuthDAO;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    UserDAO memDAO;


    @BeforeEach
    void setUp() {
        memDAO = new MemoryUserDAO();
        memDAO.addUser(new UserData("WatcherMagic", "password", "watcher@email.com"));
    }

    @Test
    void getUser() {
        UserData u = new UserData("WatcherMagic", "password", "watcher@email.com");

        assertEquals(memDAO.getUser("WatcherMagic"), u);
        assertEquals(memDAO.getUser("Bob"), null);
    }

    @Test
    void containsUser() {
        UserData u = new UserData("WatcherMagic", "password", "watcher@gmail.com");

        assertNotEquals(memDAO.containsUser("WatcherMagic"), -1);
        assertEquals(memDAO.containsUser("Joe"), -1);
    }

    @Test
    void getAuth() {
        AuthData auth = new AuthData("WatcherMagic", "123");
        AuthDAO autHDAO = new MemoryAuthDAO();
        autHDAO.addAuth(auth);

        assertEquals(autHDAO.getAuth("WatcherMagic"), auth);
    }

    @Test
    void addNewUser() {
        UserDAO user = new MemoryUserDAO();
        AuthDAO auth = new MemoryAuthDAO();
        UserService register = new UserService(user, auth);
        register.register(new UserData("WatcherMagic", "superawesomepassword", "spam"));

        assertNotEquals(user.containsUser("WatcherMagic"), -1);
        assertNotEquals(auth.getAuth("WatcherMagic"), null);
    }

    @Test
    public void authRefreshedOnSuccessfulLogin() {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        UserService userService = new UserService(userDAO, authDAO);

        userService.register(new UserData("WatcherMagic", "imafortress", "spam"));
        AuthData oldToken = authDAO.getAuth("WatcherMagic");

        //userService.login("WatcherMagic", "imafortress");
        AuthData newToken = authDAO.getAuth("WatcherMagic");

        assertNotEquals(oldToken, newToken);
    }

    @Test
    public void authRefreshFailOnUnsuccessfulLogin() {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        UserService userService = new UserService(userDAO, authDAO);

        userService.register(new UserData("WatcherMagic", "imafortress", "spam"));
        AuthData oldToken = authDAO.getAuth("WatcherMagic");

        //userService.login("WatcherMagic", "wrongpassword");
        AuthData newToken = authDAO.getAuth("WatcherMagic");

        assertEquals(oldToken, newToken);
    }

    @Test
    public void logout() {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        UserService userService = new UserService(userDAO, authDAO);

        userService.register(new UserData("WatcherMagic", "imafortress", "spam"));
        AuthData token = authDAO.getAuth("WatcherMagic");

        userService.logout(token);
        assertEquals(authDAO.getAuth(token.username()), null);
    }
}