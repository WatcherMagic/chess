
import ui.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String url = "http://localhost:8080";
        Menu menu = new Menu(url);

        int input = 0;
        while (input != 3) {
            Menu.printPreLoginUI();
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.print("You must enter an integer!\n");
                scanner.next();
                continue;
            }
            switch(input) {
                case 1: //Register
                    menu.handleRegisterUI();
                    break;
                case 2: //Login
                    menu.handleLoginUI();
                    break;
                case 3:
                    break;
                case 4: //Help
                    Menu.handleHelp(Menu.getPreLoginHelp());
                    break;
                default:
                    System.out.print("That number is not one of the valid options.\n");
                    break;
            }
        }
        System.out.print("Exited with input 3");
    }
}