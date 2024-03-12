
import ui.Menu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Menu menuHandler = new Menu();
        Scanner scanner = new Scanner(System.in);

        menuHandler.printPreLoginUI();

        int input = 0;
        while (input != 4) {
            input = scanner.nextInt();
            switch(input) {
                case 1:
                    System.out.print("Input 1!\n");
                    break;
                case 2:
                    System.out.print("Input 2!\n");
                    break;
                case 3:
                    System.out.print("Input 3!\n");
                    break;
            }
        }
        System.out.print("Exited with input 4");
    }
}