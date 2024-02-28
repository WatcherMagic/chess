package passoffTests.serverTests;

import dataAccess.MemUserDAO;
import dataAccess.objects.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemUserDAOTest {

    MemUserDAO memDAO;


    @BeforeEach
    void setUp() {
        memDAO = new MemUserDAO();
        memDAO.addUser(new User("WatcherMagic", "password", "watcher@email.com"));
    }

    @Test
    void getUser() {
        User u = new User("WatcherMagic", "password", "watcher@gmail.com");

        assertEquals(memDAO.getUser("WatcherMagic"), u);
        assertEquals(memDAO.getUser("Bob"), null);
    }

    @Test
    void containsUser() {
        User u = new User("WatcherMagic", "password", "watcher@gmail.com");

        assertNotEquals(memDAO.containsUser("WatcherMagic"), -1);
        assertEquals(memDAO.containsUser("Joe"), -1);
    }
}