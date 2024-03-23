
import ui.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String url = "http://localhost:8080";
        Menu menu = new Menu(url);

        int input = 0;
        while (input != 3) {
            Menu.printPreLoginUI();
            input = scanner.nextInt();
            switch(input) {
                case 1: //Register
                    menu.handleRegisterUI();
                    break;
                case 2: //Login
                    menu.handleLoginUI();
                    break;
                case 4: //Help
                    Menu.handlePreLoginHelp();
                    break;
            }
        }
        System.out.print("Exited with input 3");
    }
}