package Commands;
import Librarysystems.*;

import java.util.Scanner;

public class UserLoginCommand implements Command {

    private UserHandler userHandler;
    private Scanner scanner;
    private static String LOGGEDIN_USERNAME;
    private static boolean loggedIn;

    public UserLoginCommand(Scanner scanner){
        this.userHandler = UserHandler.getInstance();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        System.out.println("Enter your username: ");
        LOGGEDIN_USERNAME = scanner.nextLine().trim();
        loggedIn = userHandler.login(LOGGEDIN_USERNAME, false, 0);

    }

    public static boolean getLoggedIn(){
        return loggedIn;
    }

    public static String getLOGGEDIN_USERNAME(){
        return LOGGEDIN_USERNAME;
    }
}
