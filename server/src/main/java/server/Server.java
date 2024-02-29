package server;

import dataAccess.*;
import spark.*;

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
        userDAO = new MemUserDAO();
        authDAO = new MemAuthDAO();
        gameDAO = new MemGameDAO();
    }

    private void createRoutes() {

        UserService userService = new UserService(userDAO, authDAO);

        post("/user", (req, res) -> { //REGISTER
            //res = userService.register(where do I get request/userdata?)
            return res;
        });
        post("/session", (req, res) -> { //LOGIN
            //userService.login(username, password);
            return res;
        });
        delete("/session", (req, res) -> { //LOGOUT
            //userService.logout(authToken)
            return res;
        });

        GameService gameService = new GameService(authDAO, gameDAO);

        get("/game", (req, res) -> { //LIST GAMES
            //gameService.listGames(authToken)
            return res;
        });
//        post("/game", (req, res) -> { //CREATE GAME
//
//        });
//        put("/game",(req, res) -> { //JOIN GAME
//            //
//        });
//
//        delete("/db", (req, res) -> { //CLEAR APP
//
//        });

    }
}
