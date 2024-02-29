package passoffTests.serverTests;

import dataAccess.AuthDAO;
import dataAccess.MemAuthDAO;
import dataAccess.MemUserDAO;
import dataAccess.UserDAO;
import dataAccess.objects.AuthToken;
import dataAccess.objects.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.UserService;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    UserDAO memDAO;


    @BeforeEach
    void setUp() {
        memDAO = new MemUserDAO();
        memDAO.addUser(new User("WatcherMagic", "password", "watcher@email.com"));
    }

    @Test
    void getUser() {
        User u = new User("WatcherMagic", "password", "watcher@email.com");

        assertEquals(memDAO.getUser("WatcherMagic"), u);
        assertEquals(memDAO.getUser("Bob"), null);
    }

    @Test
    void containsUser() {
        User u = new User("WatcherMagic", "password", "watcher@gmail.com");

        assertNotEquals(memDAO.containsUser("WatcherMagic"), -1);
        assertEquals(memDAO.containsUser("Joe"), -1);
    }

    @Test
    void getAuth() {
        AuthToken auth = new AuthToken("WatcherMagic", "123");
        AuthDAO autHDAO = new MemAuthDAO();
        autHDAO.addAuth(auth);

        assertEquals(autHDAO.getAuth("WatcherMagic"), auth);
    }

    @Test
    void addNewUser() {
        UserDAO user = new MemUserDAO();
        AuthDAO auth = new MemAuthDAO();
        UserService register = new UserService(user, auth);
        register.register("WatcherMagic", "superawesomepassword", "spam");

        assertNotEquals(user.containsUser("WatcherMagic"), -1);
        assertNotEquals(auth.getAuth("WatcherMagic"), null);
    }

    @Test
    public void authRefreshedOnSuccessfulLogin() {
        UserDAO userDAO = new MemUserDAO();
        AuthDAO authDAO = new MemAuthDAO();
        UserService userService = new UserService(userDAO, authDAO);

        userService.register("WatcherMagic", "imafortress", "spam");
        AuthToken oldToken = authDAO.getAuth("WatcherMagic");

        userService.login("WatcherMagic", "imafortress");
        AuthToken newToken = authDAO.getAuth("WatcherMagic");

        assertNotEquals(oldToken, newToken);
    }

    @Test
    public void authRefreshFailOnUnsuccessfulLogin() {
        UserDAO userDAO = new MemUserDAO();
        AuthDAO authDAO = new MemAuthDAO();
        UserService userService = new UserService(userDAO, authDAO);

        userService.register("WatcherMagic", "imafortress", "spam");
        AuthToken oldToken = authDAO.getAuth("WatcherMagic");

        userService.login("WatcherMagic", "wrongpassword");
        AuthToken newToken = authDAO.getAuth("WatcherMagic");

        assertEquals(oldToken, newToken);
    }

    @Test
    public void logout() {
        UserDAO userDAO = new MemUserDAO();
        AuthDAO authDAO = new MemAuthDAO();
        UserService userService = new UserService(userDAO, authDAO);

        userService.register("WatcherMagic", "imafortress", "spam");
        AuthToken token = authDAO.getAuth("WatcherMagic");

        userService.logout(token);
        assertEquals(authDAO.getAuth(token.username()), null);
    }
}