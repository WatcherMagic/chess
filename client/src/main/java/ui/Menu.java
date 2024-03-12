package ui;

public class Menu {

    private static String preLoginUI = """
            Enter a number to select:
            
            1. Register
            2. Login
            3. Help
            4. Quit
            """;

    public static void printPreLoginUI() {
        System.out.print(preLoginUI);
    }

}
