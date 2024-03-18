package fascade;

import service.LoginAndRegisterResponse;
import spark.Spark;

public class ServerFascade {

    public int run (int port) {
        Spark.port(port);

        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public LoginAndRegisterResponse register(LoginAndRegisterResponse request) {
        System.out.println("Registered!");
        return new LoginAndRegisterResponse(null, "username", "token");
    }

    public LoginAndRegisterResponse login(LoginAndRegisterResponse request) {
        System.out.println("Logged in!");
        return new LoginAndRegisterResponse(null, "username", "token");
    }
}
