package server;

import com.google.gson.Gson;
import dataAccess.*;
import dataAccess.memory.MemoryAuthDAO;
import dataAccess.memory.MemoryGameDAO;
import dataAccess.memory.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import service.*;
import spark.*;
import spark.Response;

import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;

public class Server {

    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.init();

        // Register your endpoints and handle exceptions here.
        initializeDAOs();
        createRoutes();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void initializeDAOs() {
        userDAO = new SQLUserDAO();
        authDAO = new SQLAuthDAO();
        gameDAO = new SQLGameDAO();
    }

    private void createRoutes() {

        Gson serializer = new Gson();
        UserService userService = new UserService(userDAO, authDAO);

        post("/user", (req, res) -> { //REGISTER
            UserData u = serlializeUser(serializer, req);

            LoginAndRegisterResponse r = userService.register(u);
            res.body(serializer.toJson(r));

            res.status(userService.getErrorCode());
            return res.body();
        });
        post("/session", (req, res) -> { //LOGIN
            UserData u = serlializeUser(serializer, req);

            LoginAndRegisterResponse r = userService.login(u);
            res.body(serializer.toJson(r));

            res.status(userService.getErrorCode());
            return res.body();
        });
        delete("/session", (req, res) -> { //LOGOUT
            AuthData auth = authDAO.getAuthFromToken(getAuthStr(req));

            LoginAndRegisterResponse r = userService.logout(auth);
            res.body(serializer.toJson(r));

            res.status(userService.getErrorCode());
            return res.body();
        });

        GameService gameService = new GameService(authDAO, gameDAO);

        post("/game", (req, res) -> { //CREATE GAME
            GameRequest game = serializeGame(serializer, req);
            AuthData auth = authDAO.getAuthFromToken(getAuthStr(req));

            GameResponse r = gameService.newGame(game, auth);
            res.body(serializer.toJson(r));

            res.status(gameService.getErrorCode());
            return res.body();
        });
        get("/game", (req, res) -> { //LIST GAMES
            AuthData auth = authDAO.getAuthFromToken(getAuthStr(req));

            GameListResponse r = gameService.listGames(auth);
            res.body(serializer.toJson(r));

            res.status(gameService.getErrorCode());
            return res.body();
        });
        put("/game",(req, res) -> { //JOIN GAME
            GameRequest join = serializeGame(serializer, req);
            AuthData auth = authDAO.getAuthFromToken(getAuthStr(req));

            GameResponse r = gameService.joinGame(auth, join.gameID(), join.playerColor());
            res.body(serializer.toJson(r));

            res.status(gameService.getErrorCode());
            return res.body();
        });

        delete("/db", this::clearDatabase); //DELETE

    }

    private Object clearDatabase(Request req, Response res) throws DataAccessException {
        if (gameDAO.clearData() && authDAO.clearData() && userDAO.clearData()) {
            res.status(200);
            res.body("{}");
            return res.body();
        }
        res.status(500);
        res.body("{\"message\": \"Error: description\"}");
        return res.body();
    }

    private UserData serlializeUser(Gson serializer, spark.Request req) {
        return serializer.fromJson(req.body(), UserData.class);
    }

    private GameRequest serializeGame(Gson serializer, spark.Request req) {
        return serializer.fromJson(req.body(), GameRequest.class);
    }

    private String getAuthStr(spark.Request req) {
        String token = req.headers("authorization");
        return token;
    }
}
