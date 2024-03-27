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
        Exception ex = Assertions.assertThrows(Exception.class, () -> fascade.register(new UserData(existsUsername, existsPassword, existsEmail)));
        Assertions.assertTrue(ex.getMessage().contains("403"));
    }

    @Test
    void registerFailBadRequest() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> fascade.register(new UserData(existsUsername, existsPassword, null)));
        Assertions.assertTrue(ex.getMessage().contains("400"));
    }

    @Test
    void loginSuccess() throws Exception {
        LoginAndRegisterResponse res = fascade.login(new UserData(existsUsername, existsPassword, null));
        Assertions.assertTrue(res.getAuthToken().length() > 10);
    }

    @Test
    void loginFailUnauthorized() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> fascade.login(new UserData(existsUsername, "wrongpassword", null)));
        Assertions.assertTrue(ex.getMessage().contains("401"));
    }

    @Test
    void logoutSuccess() throws Exception {
        LoginAndRegisterResponse res = fascade.logout(existsAuth);
        Assertions.assertNull(res.getMessage());
    }

    @Test
    void logoutFailUnauthorized() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> fascade.logout(new AuthData(existsUsername, "bad token")));
        Assertions.assertTrue(ex.getMessage().contains("401"));
    }

    @Test
    void createGameSuccess() throws Exception {
        GameResponse res = fascade.createGame(new GameRequest("NewGame", null, null), existsAuth);
        Assertions.assertNotNull(res.getGameID());
        Assertions.assertEquals(2, res.getGameID());
    }

    @Test
    void createGameFailUnauthorized() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> fascade.createGame(new GameRequest("NewGame", null, null),
                new AuthData(existsUsername, "bad token")));
        Assertions.assertTrue(ex.getMessage().contains("401"));
    }

    @Test
    void createGameBadRequest() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> fascade.createGame(new GameRequest(null, null, null), existsAuth));
        Assertions.assertTrue(ex.getMessage().contains("400"));
    }

    @Test
    void joinGameSuccess() throws Exception {
        GameResponse res = fascade.joinGame(new GameRequest(null, 1, "white"), existsAuth);
        Assertions.assertNull(res.getMessage());
    }

    @Test
    void joinGameFailUnauthorized() throws Exception {
        Exception ex = Assertions.assertThrows(Exception.class, () -> fascade.joinGame(new GameRequest(null, 1, null),
                        new AuthData(existsUsername, "bad token")));
        Assertions.assertTrue(ex.getMessage().contains("401"));
    }

    @Test
    void joinGameFailBadRequestNoID() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> fascade.joinGame(new GameRequest(null, null, null), existsAuth));
        Assertions.assertTrue(ex.getMessage().contains("400"));
    }

    @Test
    void joinGameFailIDNotExists() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> fascade.joinGame(new GameRequest(null, 100, null), existsAuth));
        Assertions.assertTrue(ex.getMessage().contains("400"));
    }

    @Test
    void joinGameFailSpotTaken() throws Exception {
        fascade.joinGame(new GameRequest(null, 1, "white"), existsAuth);
        Exception ex = Assertions.assertThrows(Exception.class, () -> fascade.joinGame(
                new GameRequest(null, 1, "white"), existsAuth));
        Assertions.assertTrue(ex.getMessage().contains("403"));
    }

    @Test
    void listGamesSuccess() throws Exception {
        GameListResponse res = fascade.listGames(existsAuth);
        Assertions.assertTrue(!res.getGameList().isEmpty());
    }

    @Test
    void ListGamesFailUnauthorized() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> fascade.listGames(new AuthData(existsUsername, "bad token")));
        Assertions.assertTrue(ex.getMessage().contains("401"));
    }
}
