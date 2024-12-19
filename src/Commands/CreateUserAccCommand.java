package Commands;

import java.util.Scanner;
import Librarysystems.*;

public class CreateUserAccCommand implements Command {

    private UserHandler userHandler;
    private Scanner scanner;
    private static String LOGGEDIN_USERNAME;
    private static boolean loggedIn;

    public CreateUserAccCommand(Scanner scanner) {
        this.userHandler = UserHandler.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        LOGGEDIN_USERNAME = userHandler.createUserAccount();
        System.out.println("Account created successfully!");
        loggedIn = true;
    }

    public static String getLOGGEDIN_USERNAME() {
        return LOGGEDIN_USERNAME;
    }

    public static boolean getLoggedIn() {
        return loggedIn;
    }
}
