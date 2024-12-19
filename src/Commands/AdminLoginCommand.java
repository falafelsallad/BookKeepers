package Commands;

import java.util.Scanner;
import Librarysystems.*;

public class AdminLoginCommand implements Command {

    private UserHandler userHandler;
    private Scanner scanner;
    private static String LOGGEDIN_USERNAME;
    private static boolean loggedIn;

    public AdminLoginCommand(UserHandler userHandler, Scanner scanner) {
        this.userHandler = UserHandler.getInstance();
        this.scanner = scanner;
    }
    @Override
    public void execute() {
        System.out.println("Enter username: ");
        LOGGEDIN_USERNAME = scanner.nextLine().trim();
        loggedIn = userHandler.login(LOGGEDIN_USERNAME, true, 3);
    }

    public static boolean getLoggedIn() {
        return loggedIn;
    }

    public static String getLOGGEDIN_USERNAME() {
        return LOGGEDIN_USERNAME;
    }
}
