package ui;

import fascade.ServerFascade;
import model.UserData;

import java.util.Scanner;

public class Menu {

    static Scanner scanner = new Scanner(System.in);

    private static String preLoginHelp = """
            Type "1" in the console and press "Enter" to create a new user.
            Type "2" to log in as an existing user.
            Type "3" to close the program.
            To exit help, press "Enter" or enter any character.
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

    public static void handleRegisterUI(String serverURL) throws Exception {

        System.out.print("Choose your username\n");
        String username = scanner.nextLine();

        System.out.print("Enter an email\n");
        String email = scanner.nextLine();

        System.out.print("And create your password\n");
        String password = scanner.nextLine();

        ServerFascade serverFascade = new ServerFascade(serverURL);

        serverFascade.register(new UserData(username, password, email));
    }

    public static void handleLoginUI(String serverURL) throws Exception {

        System.out.print("Enter your username\n");
        String username = scanner.nextLine();

        System.out.print("Enter your password\n");
        String password = scanner.nextLine();

        ServerFascade serverFascade = new ServerFascade(serverURL);

        serverFascade.login(new UserData(username, password, null));
    }

    public static void handlePreLoginHelp() {
        System.out.print(preLoginHelp);
        scanner.nextLine();
    }
}
