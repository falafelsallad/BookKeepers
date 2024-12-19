import java.util.Scanner;

public class CreateAdminAccCommand implements Command {

    private LibrarySystem librarySystem;
    private Scanner scanner;
    private static String LOGGEDIN_USERNAME;
    private static boolean loggedIn;

    public CreateAdminAccCommand(LibrarySystem librarySystem, Scanner scanner) {
        this.librarySystem = LibrarySystem.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        LOGGEDIN_USERNAME = librarySystem.createAdminAccount();
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
