
import server.Server;
import ui.Menu;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Menu menuHandler = new Menu();
        Scanner scanner = new Scanner(System.in);
        String url = "http://localhost:8080";

        menuHandler.printPreLoginUI();

        int input = 0;
        while (input != 4) {
            input = scanner.nextInt();
            switch(input) {
                case 1: //Register
                    menuHandler.handleRegisterUI(url);
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