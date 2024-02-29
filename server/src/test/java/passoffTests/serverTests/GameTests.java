package passoffTests.serverTests;

import dataAccess.*;
import dataAccess.objects.AuthToken;
import dataAccess.objects.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.GameService;
import server.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GameTests {

    @Test
    public void createGame() {
        AuthDAO authDAO = new MemAuthDAO();
        GameDAO gameDAO = new MemGameDAO();
        GameService service = new GameService(authDAO, gameDAO);

        //
    }

    @Test
    public void listGames() {
        AuthDAO authDAO = new MemAuthDAO();
        GameDAO gameDAO = new MemGameDAO();
        GameService service = new GameService(authDAO, gameDAO);

        //
    }

}