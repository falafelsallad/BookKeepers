package Commands;

import java.util.Scanner;
import Librarysystems.*;

public class CreateAdminAccCommand implements Command {

    private UserHandler userHandler;
    private Scanner scanner;
    private static String LOGGEDIN_USERNAME;
    private static boolean loggedIn;

    public CreateAdminAccCommand(Scanner scanner) {
        this.userHandler = UserHandler.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        LOGGEDIN_USERNAME = userHandler.createAdminAccount();
        System.out.println("Admin account created successfully!");
        loggedIn = true;
    }

    public static String getLOGGEDIN_USERNAME() {
        return LOGGEDIN_USERNAME;
    }

    public static boolean getLoggedIn() {
        return loggedIn;
    }
}
