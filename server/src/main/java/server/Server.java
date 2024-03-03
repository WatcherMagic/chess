package server;

import com.google.gson.Gson;
import dataAccess.*;
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
    Gson serializer = new Gson();


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
        userDAO = new MemUserDAO();
        authDAO = new MemAuthDAO();
        gameDAO = new MemGameDAO();
    }

    //no gameName: bad request
    //null res obj: bad request
    //joingame: no game ID = bad req

    private void createRoutes() {

        UserService userService = new UserService(userDAO, authDAO);

        post("/user", (req, res) -> { //REGISTER
            UserData newUser = serializer.fromJson(req.body(), UserData.class);
            LoginAndRegisterResponse r = userService.register(newUser);
            String s = serializer.toJson(r);
            res.body(s);
            if (userService.getErrorCode() != 0) {
                if (userService.getErrorCode() == 400) { //bad request
                    res.status(400);
                }
                else if (userService.getErrorCode() == 403) { //username already taken
                    res.status(403);
                }
                else {
                    res.status(500);
                }
            }
            else { //success
                res.status(200);
            }
            return res.body();

            //check to make sure status code is correct
        });
        post("/session", (req, res) -> { //LOGIN
            UserData in = serializer.fromJson(req.body(), UserData.class);
            LoginAndRegisterResponse r = userService.login(in);
            res.body(serializer.toJson(r));
            if (!r.userInfoNull()) { //username already taken
                res.status(200);
                return res.body();
            }
            else {
                if (userService.getErrorCode() == 401) {
                    res.status(401);
                    return res.body();
                }
                else {
                    res.status(500);
                    return res.body();
                }
            }
        });
        delete("/session", (req, res) -> { //LOGOUT
            String authStr = req.headers("authorization");
            AuthData auth = authDAO.getAuthFromToken(authStr);
            LoginAndRegisterResponse out = userService.logout(auth);
            String json = serializer.toJson(out);
            res.body(json);

            int error = userService.getErrorCode();
            if (userService.getErrorCode() != 0) {
                if (userService.getErrorCode() == 401) { //unauthorized
                    res.status(401);
                }
                else {
                    res.status(500);
                }
            }
            else { //success
                res.status(200);
            }
            return res.body();
        });

        GameService gameService = new GameService(authDAO, gameDAO);

        post("/game", (req, res) -> { //CREATE GAME
            GameRequest newGame = serializer.fromJson(req.body(), GameRequest.class);
            String authStr = req.headers("authorization");
            AuthData auth = authDAO.getAuthFromToken(authStr);
            GameResponse r = gameService.newGame(newGame, auth);
            res.body(serializer.toJson(r));

            if (gameService.getErrorCode() != 0) {
                if (gameService.getErrorCode() == 400) { //bad request
                    res.status(400);
                }
                else if (gameService.getErrorCode() == 401) {
                    res.status(401);
                }
                else if (gameService.getErrorCode() == 403) { //username already taken
                    res.status(403);
                }
                else {
                    res.status(500);
                }
            }
            else { //success
                res.status(200);
            }
            return res.body();
        });
        get("/game", (req, res) -> { //LIST GAMES
            String authStr = req.headers("authorization");
            AuthData auth = authDAO.getAuthFromToken(authStr);
            GameListResponse r = gameService.listGames(auth);
            String s = serializer.toJson(r);
            res.body(s);

            if (gameService.getErrorCode() != 0) {
                if (gameService.getErrorCode() == 401) {
                    res.status(401);
                }
                else {
                    res.status(500);
                }
            }
            else { //success
                res.status(200);
            }
            return res.body();
        });
        put("/game",(req, res) -> { //JOIN GAME
            GameRequest joinGame = serializer.fromJson(req.body(), GameRequest.class);
            String authStr = req.headers("authorization");
            AuthData auth = authDAO.getAuthFromToken(authStr);
            GameResponse r = gameService.joinGame(auth, joinGame.gameID(), joinGame.playerColor());
            res.body(serializer.toJson(r));

            if (gameService.getErrorCode() != 0) {
                if (gameService.getErrorCode() == 400) { //bad request
                    res.status(400);
                }
                else if (gameService.getErrorCode() == 401) {
                    res.status(401);
                }
                else if (gameService.getErrorCode() == 403) { //username already taken
                    res.status(403);
                }
                else {
                    res.status(500);
                }
            }
            else { //success
                res.status(200);
            }
            return res.body();
        });

        delete("/db", this::clearDatabase); //DELETE

    }

    private Object clearDatabase(Request req, Response res) {
        userDAO.clearData();
        authDAO.clearData();
        gameDAO.clearData();

        res.status(200);
        res.body("");
        return res.body();
    }
}
