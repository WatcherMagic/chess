package passoffTests.serverTests;

import dataAccess.*;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.Test;
import service.GameService;
import service.UserService;
import service.GameListResponse;
import service.GameRequest;
import service.GameResponse;

import static org.junit.jupiter.api.Assertions.*;

class GameTests {

    @Test
    public void createGame() {
        UserDAO userDAO = new MemUserDAO();
        AuthDAO authDAO = new MemAuthDAO();
        GameDAO gameDAO = new MemGameDAO();
        GameService service = new GameService(authDAO, gameDAO);
        UserService user = new UserService(userDAO, authDAO);

        user.register(new User("WatcherMagic", "password", "email"));
        AuthToken userAuth = authDAO.getAuth("WatcherMagic");
        //service.newGame(userAuth, "Game1");

        assertEquals(1, gameDAO.getGameList().size());
    }

    @Test
    public void joinGame() {
        UserDAO userDAO = new MemUserDAO();
        AuthDAO authDAO = new MemAuthDAO();
        GameDAO gameDAO = new MemGameDAO();
        GameService service = new GameService(authDAO, gameDAO);
        UserService user = new UserService(userDAO, authDAO);

        user.register(new User("WatcherMagic", "password", "email"));
        AuthToken userAuth = authDAO.getAuth("WatcherMagic");
//        int gameID = service.newGame(userAuth, "Game1");
//        service.joinGame(userAuth, gameID, ChessGame.TeamColor.WHITE);
//        assertEquals(gameDAO.getGameData(gameID).whiteUsername(), userAuth.username());
//
//        user.register(new User("Weee", "password", "email"));
//        AuthToken userAuth2 = authDAO.getAuth("Weee");
//        service.joinGame(userAuth2, gameID, ChessGame.TeamColor.BLACK);
//        assertEquals(gameDAO.getGameData(gameID).blackUsername(), userAuth2.username());
    }

    @Test
    public void oneAuthPerUser() {
        UserDAO userDAO = new MemUserDAO();
        AuthDAO authDAO = new MemAuthDAO();
        GameDAO gameDAO = new MemGameDAO();
        GameService gameService = new GameService(authDAO, gameDAO);
        UserService userService = new UserService(userDAO, authDAO);

        User user = new User("CoolName", "1234", "mail");
        userService.register(user);
        userService.logout(authDAO.getAuth(user.username()));
        assertEquals(authDAO.getAuth(user.username()), null, "There is an auth token for a user when there shouldn't be");
        userService.login(user);
        AuthToken userAuth = authDAO.getAuth(user.username());
        assertNotEquals(userAuth, null, "The user is missing an auth token when they should have one");

        GameResponse game1 = gameService.newGame(new GameRequest("Game1", null, null), userAuth);
        GameResponse game2 = gameService.newGame(new GameRequest("Game2", null, null), userAuth);
        GameListResponse l = gameService.listGames(userAuth);
//        assertNotNull(l.getGameList(), "the list of games is empty or was not returned");

        gameService.joinGame(userAuth, game1.getGameID(), "WHITE");
        assertEquals(gameDAO.getGameData(game1.getGameID()).whiteUsername(), user.username(), "the new player was not properly added");

        userService.logout(userAuth);
        authDAO.clearData();
        userDAO.clearData();
        gameDAO.clearData();
    }

}