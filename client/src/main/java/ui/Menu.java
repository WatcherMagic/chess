package ui;

import fascade.ServerFascade;
import model.UserData;
import service.LoginAndRegisterResponse;

import java.util.Scanner;

public class Menu {

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

    public void handleRegisterUI(String serverURL) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username\n");
        String username = scanner.nextLine();

        System.out.print("Now enter an email\n");
        String email = scanner.nextLine();

        System.out.print("Create your password\n");
        String password = scanner.nextLine();

        ServerFascade serverFascade = new ServerFascade(serverURL);

        serverFascade.register(new UserData(username, password, email));
    }

}
