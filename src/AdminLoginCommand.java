import java.util.Scanner;

public class AdminLoginCommand implements Command{

    private LibrarySystem librarySystem;
    private Scanner scanner;
    private static String LOGGEDIN_USERNAME;
    private static boolean loggedIn;

    public AdminLoginCommand(LibrarySystem librarySystem, Scanner scanner) {
        this.librarySystem = LibrarySystem.getInstance();
        this.scanner = scanner;
    }
    @Override
    public void execute() {
        System.out.println("Enter username: ");
        LOGGEDIN_USERNAME = scanner.nextLine().trim();
        loggedIn = librarySystem.login(LOGGEDIN_USERNAME, true, 3);
    }

    public static boolean getLoggedIn() {
        return loggedIn;
    }

    public static String getLOGGEDIN_USERNAME() {
        return LOGGEDIN_USERNAME;
    }
}
