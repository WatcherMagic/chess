package clientTests;

import fascade.ServerFascade;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import service.GameListResponse;
import service.GameRequest;
import service.GameResponse;
import service.LoginAndRegisterResponse;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFascade fascade;
    private static String existsUsername = "player1";
    private static String existsPassword = "password";
    private static String existsEmail = "email";
    private AuthData existsAuth;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        fascade = new ServerFascade("http://localhost:" + port);
    }

    @BeforeEach
    public void clearDBAndAddDefaults() throws Exception {
        server.clearDatabase();
        existsAuth = new AuthData(existsUsername, fascade.register(new UserData(existsUsername, existsPassword, existsEmail)).getAuthToken());
        fascade.createGame(new GameRequest("Game", null, null), existsAuth).getGameID();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    void registerSuccess() throws Exception {
        LoginAndRegisterResponse res = fascade.register(new UserData("player2", "password", "p1@email.com"));
        Assertions.assertTrue(res.getAuthToken().length() > 10);
    }

    @Test
    void registerFailAlreadyTaken() throws Exception {
        LoginAndRegisterResponse res = fascade.register(new UserData(existsUsername, existsPassword, existsEmail));
        Assertions.assertNotNull(res.getMessage());
        assertEquals("Error: already taken", res.getMessage());
    }

    @Test
    void registerFailBadRequest() throws Exception {
        LoginAndRegisterResponse res = fascade.register(new UserData(existsUsername, existsPassword, null));
        Assertions.assertNotNull(res.getMessage());
        assertEquals("Error: bad request", res.getMessage());
    }

    @Test
    void loginSuccess() throws Exception {
        LoginAndRegisterResponse res = fascade.login(new UserData(existsUsername, existsPassword, null));
        Assertions.assertTrue(res.getAuthToken().length() > 10);
    }

    @Test
    void loginFailUnauthorized() throws Exception {
        LoginAndRegisterResponse res = fascade.login(new UserData(existsUsername, "wrongpassword", null));
        Assertions.assertNotNull(res.getMessage());
        assertEquals("Error: unauthorized", res.getMessage());
    }

    @Test
    void logoutSuccess() throws Exception {
        LoginAndRegisterResponse res = fascade.logout(existsAuth);
        Assertions.assertNull(res.getMessage());
    }

    @Test
    void logoutFailUnauthorized() throws Exception {
        LoginAndRegisterResponse res = fascade.logout(new AuthData(existsUsername, "bad token"));
        Assertions.assertNotNull(res.getMessage());
        assertEquals("Error: unauthorized", res.getMessage());
    }

    @Test
    void createGameSuccess() throws Exception {
        GameResponse res = fascade.createGame(new GameRequest("NewGame", null, null), existsAuth);
        Assertions.assertNotNull(res.getGameID());
        Assertions.assertEquals(2, res.getGameID());
    }

    @Test
    void createGameFailUnauthorized() throws Exception {
        GameResponse res = fascade.createGame(new GameRequest("NewGame", null, null),
                new AuthData(existsUsername, "bad token"));
        Assertions.assertNotNull(res.getMessage());
        assertEquals("Error: unauthorized", res.getMessage());
    }

    @Test
    void createGameBadRequest() throws Exception {
        GameResponse res = fascade.createGame(new GameRequest(null, null, null), existsAuth);
        Assertions.assertNotNull(res.getMessage());
        assertEquals("Error: bad request", res.getMessage());
    }

    @Test
    void joinGameSuccess() throws Exception {
        GameResponse res = fascade.joinGame(new GameRequest(null, 1, "white"), existsAuth);
        Assertions.assertNull(res.getMessage());
    }

    @Test
    void joinGameFailUnauthorized() throws Exception {
        GameResponse res = fascade.joinGame(new GameRequest(null, 1, null),
                        new AuthData(existsUsername, "bad token"));
        Assertions.assertNotNull(res.getMessage());
        assertEquals("Error: unauthorized", res.getMessage());
    }

    @Test
    void joinGameFailBadRequestNoID() throws Exception {
        GameResponse res = fascade.joinGame(new GameRequest(null, null, null), existsAuth);
        Assertions.assertNotNull(res.getMessage());
        assertEquals("Error: bad request", res.getMessage());
    }

    @Test
    void joinGameFailBadRequestIDNotExists() throws Exception {
        GameResponse res = fascade.joinGame(new GameRequest(null, 100, null), existsAuth);
        Assertions.assertNotNull(res.getMessage());
        assertEquals("Error: bad request", res.getMessage());
    }

    @Test
    void joinGameFailSpotTaken() throws Exception {
        fascade.joinGame(new GameRequest(null, 1, "white"), existsAuth);
        GameResponse res = fascade.joinGame(
                new GameRequest(null, 1, "white"), existsAuth);
        Assertions.assertNotNull(res.getMessage());
        assertEquals("Error: already taken", res.getMessage());
    }

    @Test
    void listGamesSuccess() throws Exception {
        GameListResponse res = fascade.listGames(existsAuth);
        assertFalse(res.getGameList().isEmpty());
    }

    @Test
    void ListGamesFailUnauthorized() throws Exception {
        GameListResponse res = fascade.listGames(new AuthData(existsUsername, "bad token"));
        Assertions.assertNotNull(res.getMessage());
        assertEquals("Error: unauthorized", res.getMessage());
    }
}
