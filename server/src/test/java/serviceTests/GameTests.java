package serviceTests;

import dataAccess.*;
import dataAccess.memory.MemoryAuthDAO;
import dataAccess.memory.MemoryGameDAO;
import dataAccess.memory.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.GameService;
import service.UserService;
import service.GameListResponse;
import service.GameRequest;
import service.GameResponse;

import static org.junit.jupiter.api.Assertions.*;

class GameTests {

    @Test
    public void createGame() throws DataAccessException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        GameService service = new GameService(authDAO, gameDAO);
        UserService user = new UserService(userDAO, authDAO);

        user.register(new UserData("WatcherMagic", "password", "email"));
        AuthData userAuth = authDAO.getAuth("WatcherMagic");
        GameRequest request = new GameRequest("Game1", null, null);
        service.newGame(request, userAuth);

        assertEquals(1, gameDAO.getGameList().size());
    }

    @Test
    public void joinGame() throws DataAccessException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        GameService service = new GameService(authDAO, gameDAO);
        UserService user = new UserService(userDAO, authDAO);

        user.register(new UserData("WatcherMagic", "password", "email"));
        AuthData userAuth = authDAO.getAuth("WatcherMagic");
        GameRequest request = new GameRequest("Game1", null, null);
        GameResponse response = service.newGame(request, userAuth);
        service.joinGame(userAuth, response.getGameID(), "WHITE");
        assertEquals(gameDAO.getGameData(response.getGameID()).whiteUsername(), userAuth.username());

        user.register(new UserData("Weee", "password", "email"));
        AuthData userAuth2 = authDAO.getAuth("Weee");
        service.joinGame(userAuth2, response.getGameID(), "BLACK");
        assertEquals(gameDAO.getGameData(response.getGameID()).blackUsername(), userAuth2.username());
    }

    @Test
    public void oneAuthPerUser() throws DataAccessException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        GameService gameService = new GameService(authDAO, gameDAO);
        UserService userService = new UserService(userDAO, authDAO);

        UserData user = new UserData("CoolName", "1234", "mail");
        userService.register(user);
        userService.logout(authDAO.getAuth(user.username()));
        assertEquals(authDAO.getAuth(user.username()), null, "There is an auth token for a user when there shouldn't be");
        userService.login(user);
        AuthData userAuth = authDAO.getAuth(user.username());
        assertNotEquals(userAuth, null, "The user is missing an auth token when they should have one");

        GameResponse game1 = gameService.newGame(new GameRequest("Game1", null, null), userAuth);
        GameResponse game2 = gameService.newGame(new GameRequest("Game2", null, null), userAuth);
        GameListResponse l = gameService.listGames(userAuth);
        assertNotNull(l.getGameList(), "the list of games is empty or was not returned");

        gameService.joinGame(userAuth, game1.getGameID(), "WHITE");
        assertEquals(gameDAO.getGameData(game1.getGameID()).whiteUsername(), user.username(), "the new player was not properly added");

        userService.logout(userAuth);
        authDAO.clearData();
        userDAO.clearData();
        gameDAO.clearData();
    }

}