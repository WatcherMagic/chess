package ui;

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

}
