package fascade;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.client.HttpResponseException;
import service.GameListResponse;
import service.GameRequest;
import service.GameResponse;
import service.LoginAndRegisterResponse;
import spark.Spark;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ServerFascade {

    private final String serverUrl;

    public ServerFascade(String serverURL) {
        this.serverUrl = serverURL;
    }

    public int run (int port) {
        Spark.port(port);

        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public LoginAndRegisterResponse register(UserData userData) throws Exception {
        return makeRequest("POST", "/user", userData, null, LoginAndRegisterResponse.class);
    }

    public LoginAndRegisterResponse login(UserData userData) throws Exception {
        return makeRequest("POST", "/session", userData, null, LoginAndRegisterResponse.class);
    }

    public LoginAndRegisterResponse logout(AuthData auth) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", auth.token());
        return makeRequest("DELETE", "/session", auth, headers, LoginAndRegisterResponse.class);
    }

    public GameResponse createGame(GameRequest request, AuthData auth) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", auth.token());
        return makeRequest("POST", "/game", request, headers, GameResponse.class);
    }

    public GameResponse joinGame(GameRequest request) throws Exception {
        return makeRequest("PUT", "/game", request, null, GameResponse.class);
    }

    public GameListResponse listGames(GameRequest request) throws Exception {
        return makeRequest("GET", "/game", request, null, GameListResponse.class);
    }

    private <T> T makeRequest(String method, String path, Object request, Map<String, String> headers, Class<T> responseClass) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    http.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            http.setDoOutput(true);
            writeBody(request, http);

            http.connect();

            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, responseClass);
            }
        } catch (Exception ex) {
            throw new Exception();
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }
}
