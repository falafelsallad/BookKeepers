import java.util.Scanner;

public class UserLoginCommand implements Command{

    private LibrarySystem librarySystem;
    private Scanner scanner;
    private static String LOGGEDIN_USERNAME;
    private static boolean loggedIn;

    public UserLoginCommand(LibrarySystem librarySystem, Scanner scanner){
        this.librarySystem = LibrarySystem.getInstance();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        System.out.println("Enter your username: ");
        LOGGEDIN_USERNAME = scanner.nextLine().trim();
        loggedIn = librarySystem.login(LOGGEDIN_USERNAME, false, 0);
    }

    public static boolean getLoggedIn(){
        return loggedIn;
    }

    public static String getLOGGEDIN_USERNAME(){
        return LOGGEDIN_USERNAME;
    }
}
