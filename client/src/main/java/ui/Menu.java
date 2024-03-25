package ui;

import fascade.ServerFascade;
import model.AuthData;
import model.GameData;
import model.UserData;
import service.GameListResponse;
import service.GameRequest;
import service.GameResponse;
import service.LoginAndRegisterResponse;
import static ui.EscapeSequences.*;

import java.util.List;
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
            
            Press "Enter" or enter any character to continue.
            """;

    private static String postLoginHelp = """
            Type "1" in the console and press "Enter" to create a new game,
            "2" to list already existing games,
            "3" to join a game as player or observer,
            and "4" to exit to the menu.
            
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
            4. Logout
            5. Help
            """;

    public static void printPreLoginUI() {
        System.out.print(preLoginUI);
    }

    public static String getPreLoginHelp() {
        return preLoginHelp;
    }

    public static void handleHelp(String help) {
        System.out.print(help);
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
        while (input != 4) {
            System.out.print(postLoginUI);
            input = Integer.parseInt(scanner.nextLine());
            switch(input) {
                case 1: //Create game
                    handleCreateGameUI(auth);
                    break;
                case 2:
                    handleListGamesUI(auth);
                    break;
                case 3:
                    handleJoinGameUI(auth);
                    break;
                case 4:
                    fascade.logout(auth);
                    break;
                case 5:
                    handleHelp(postLoginHelp);
                    break;
            }
        }
    }

    private void handleCreateGameUI(AuthData auth) throws Exception {
        System.out.print("Enter a name for your game:\n");
        String name = scanner.nextLine();

        GameRequest req = new GameRequest(name, null, null);
        GameResponse res = fascade.createGame(req, auth);
        if (res.getMessage() == null) {
            System.out.print("Successfully created " + name + "!\n");
        }
        else {
            System.out.print(res.getMessage() + "\n");
        }
        scanner.nextLine();
    }

    private void handleListGamesUI(AuthData auth) throws Exception {
        GameListResponse res = fascade.listGames(auth);
        if (res.getMessage() == null) {
            List<GameData> gameList = res.getGameList();

            Boolean colorAlternate = false;
            System.out.print("   ID   NAME\n");
            for (int i = 0; i < gameList.size(); i++) {
                if (colorAlternate == false) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                }
                System.out.print((i + 1) + " | " + gameList.get(i).gameID() + " | " + gameList.get(i).gameName() + SET_BG_COLOR_WHITE + "\n");
                colorAlternate = !colorAlternate;
            }
            System.out.print("\nPress \"Enter\" to return to the menu.\n");
        }
        else {
            System.out.print(res.getMessage() + "\n");
        }
        scanner.nextLine();
    }

    private void handleJoinGameUI(AuthData auth) throws Exception {
        System.out.print("Enter the Game ID of the game you'd like to join:\n");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Now type the word \"White\" or \"Black\" to choose your team, or leave blank to join as an observer.\n");
        String team = scanner.nextLine();

        GameResponse res = fascade.joinGame(new GameRequest(null, id, team), auth);
        if (res.getMessage() == null) {
            System.out.print("You've successfully joined!\n");
        }
        else {
            System.out.print(res.getMessage() + "\n");
        }
        scanner.nextLine();
    }
}
