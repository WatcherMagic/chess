//import chess.*;
import server.Server;
import spark.*;


public class Main {
    public static void main(String[] args) {

        Server server = new Server();
        server.run(8080);

    }
}