package ui;

import com.google.gson.Gson;
import fascade.ServerFascade;
import model.AuthData;
import model.UserData;
import service.GameRequest;
import service.LoginAndRegisterResponse;

import java.util.Scanner;

public class Menu {

    static Scanner scanner = new Scanner(System.in);
    static ServerFascade fascade;

    public Menu(String serverURL) {
        fascade = new ServerFascade(serverURL);
    }

    private static String preLoginHelp = """
            Type "1" in the console and press "Enter" to create a new user.
            Type "2" to log in as an existing user.
            Type "3" to close the program.
            To exit help, press "Enter" or enter any character.
            """;

    private static String postLoginHelp = """
            Type "1" in the console and press "Enter" to create a new game,
            "2" to list already existing games,
            "3" to play a game,
            "4" to watch a game,
            and "5" to exit to the main menu.
            Press "Enter" or enter any character to continue.
            """;

    private static String preLoginUI = """
            Enter a number to select:
            
            1. Register
            2. Login
            3. Quit
            4. Help
            """;

    private static String postLoginUI = """
            Enter a number to select:
            
            1. Create Game
            2. List Games
            3. Join Game
            4. Observe Game
            5. Logout
            6. Help
            """;

    public static void printPreLoginUI() {
        System.out.print(preLoginUI);
    }

    public static void handlePreLoginHelp() {
        System.out.print(preLoginHelp);
        scanner.nextLine();
    }

    private static void handlePostLoginHelp() {
        System.out.print(postLoginHelp);
        scanner.nextLine();
    }

    public void handleRegisterUI() throws Exception {

        System.out.print("Choose your username\n");
        String username = scanner.nextLine();

        System.out.print("Enter an email\n");
        String email = scanner.nextLine();

        System.out.print("And create your password\n");
        String password = scanner.nextLine();

        LoginAndRegisterResponse res = fascade.register(new UserData(username, password, email));
        System.out.print("Succesfully registered! Logging in...");

        AuthData auth = new AuthData(res.getUsername(), res.getAuthToken());

        handlePostLoginUI(auth);
    }

    public void handleLoginUI() throws Exception {

        System.out.print("Enter your username\n");
        String username = scanner.nextLine();

        System.out.print("Enter your password\n");
        String password = scanner.nextLine();

        LoginAndRegisterResponse res = fascade.login(new UserData(username, password, null));
        if (res.getMessage() == null) {
            handlePostLoginUI(new AuthData(res.getUsername(), res.getAuthToken()));
        }
    }

    private void handlePostLoginUI(AuthData auth) throws Exception {
        int input = 0;
        while (input != 5) {
            System.out.print(postLoginUI);
            input = Integer.parseInt(scanner.nextLine());
            switch(input) {
                case 1: //Create game
                    handleCreateGameUI();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    fascade.logout(auth);
                    break;
                case 6:
                    break;
            }
        }
    }

    private void handleCreateGameUI() throws Exception {
        System.out.print("Enter a name for your game:\n");
        String name = scanner.nextLine();

        GameRequest request = new GameRequest(name, null, null);
        fascade.createGame(request);
        System.out.print("Successfully created " + name + "!\n");
        scanner.nextLine();
    }
}
