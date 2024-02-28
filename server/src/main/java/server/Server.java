package server;

import dataAccess.MemAuthDAO;
import dataAccess.MemUserDAO;
import spark.*;

import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;

public class Server {

    MemUserDAO userDAO;
    MemAuthDAO authDAO;

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
    }

    private void createRoutes() {

        //        post("/session", (req, res) -> { //LOGIN
//
//        });
//        delete("/session", (req, res) -> { //LOGOUT
//
//        });

        post("/user", (req, res) -> { //REGISTER
            RegistrationService register = new RegistrationService(userDAO, authDAO);
            //res = register(where do I get request/userdata?)
            return res;
        });

//        get("/game", (req, res) -> { //LIST GAMES
//
//        });
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
