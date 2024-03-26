package test;

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
        assertTrue(res.getAuthToken().length() > 10);
    }

    @Test
    void registerFailAlreadyTaken() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> fascade.register(new UserData(existsUsername, existsPassword, existsEmail)));
        assertTrue(ex.getMessage().contains("403"));
    }

    @Test
    void registerFailBadRequest() {
        Exception ex = assertThrows(Exception.class, () -> fascade.register(new UserData(existsUsername, existsPassword, null)));
        assertTrue(ex.getMessage().contains("400"));
    }

    @Test
    void loginSuccess() throws Exception {
        LoginAndRegisterResponse res = fascade.login(new UserData(existsUsername, existsPassword, null));
        assertTrue(res.getAuthToken().length() > 10);
    }

    @Test
    void loginFailUnauthorized() {
        Exception ex = assertThrows(Exception.class, () -> fascade.login(new UserData(existsUsername, "wrongpassword", null)));
        assertTrue(ex.getMessage().contains("401"));
    }

    @Test
    void logoutSuccess() throws Exception {
        LoginAndRegisterResponse res = fascade.logout(existsAuth);
        assertNull(res.getMessage());
    }

    @Test
    void logoutFailUnauthorized() {
        Exception ex = assertThrows(Exception.class, () -> fascade.logout(new AuthData(existsUsername, "bad token")));
        assertTrue(ex.getMessage().contains("401"));
    }

    @Test
    void createGameSuccess() throws Exception {
        GameResponse res = fascade.createGame(new GameRequest("NewGame", null, null), existsAuth);
        assertNotNull(res.getGameID());
        assertEquals(2, res.getGameID());
    }

    @Test
    void createGameFailUnauthorized() {
        Exception ex = assertThrows(Exception.class, () -> fascade.createGame(new GameRequest("NewGame", null, null),
                new AuthData(existsUsername, "bad token")));
        assertTrue(ex.getMessage().contains("401"));
    }

    @Test
    void createGameBadRequest() {
        Exception ex = assertThrows(Exception.class, () -> fascade.createGame(new GameRequest(null, null, null), existsAuth));
        assertTrue(ex.getMessage().contains("400"));
    }

    @Test
    void joinGameSuccess() throws Exception {
        GameResponse res = fascade.joinGame(new GameRequest(null, 1, "white"), existsAuth);
        assertNull(res.getMessage());
    }

    @Test
    void joinGameFailUnauthorized() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> fascade.joinGame(new GameRequest(null, 1, null),
                        new AuthData(existsUsername, "bad token")));
        assertTrue(ex.getMessage().contains("401"));
    }

    @Test
    void joinGameFailBadRequestNoID() {
        Exception ex = assertThrows(Exception.class, () -> fascade.joinGame(new GameRequest(null, null, null), existsAuth));
        assertTrue(ex.getMessage().contains("400"));
    }

    @Test
    void joinGameFailIDNotExists() {
        Exception ex = assertThrows(Exception.class, () -> fascade.joinGame(new GameRequest(null, 100, null), existsAuth));
        assertTrue(ex.getMessage().contains("400"));
    }

    @Test
    void joinGameFailSpotTaken() throws Exception {
        fascade.joinGame(new GameRequest(null, 1, "white"), existsAuth);
        Exception ex = assertThrows(Exception.class, () -> fascade.joinGame(
                new GameRequest(null, 1, "white"), existsAuth));
        assertTrue(ex.getMessage().contains("403"));
    }

    @Test
    void listGamesSuccess() throws Exception {
        GameListResponse res = fascade.listGames(existsAuth);
        assertTrue(!res.getGameList().isEmpty());
    }

    @Test
    void ListGamesFailUnauthorized() {
        Exception ex = assertThrows(Exception.class, () -> fascade.listGames(new AuthData(existsUsername, "bad token")));
        assertTrue(ex.getMessage().contains("401"));
    }
}
