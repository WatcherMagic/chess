
import ui.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String url = "http://localhost:8080";

        int input = 0;
        while (input != 3) {
            System.out.print("\033[H\033[2J");
            Menu.printPreLoginUI();
            input = scanner.nextInt();
            switch(input) {
                case 1: //Register
                    Menu.handleRegisterUI(url);
                    break;
                case 2:
                    Menu.handleLoginUI(url);
                    break;
                case 4:
                    Menu.handlePreLoginHelp();
                    break;
            }
        }
        System.out.print("Exited with input 3");
    }
}