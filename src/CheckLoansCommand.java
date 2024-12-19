import java.util.Scanner;

public class CheckLoansCommand implements Command {

    private LibrarySystem librarySystem;
    private Scanner scanner;

    public CheckLoansCommand(LibrarySystem librarySystem, Scanner scanner) {
        this.librarySystem = LibrarySystem.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        if (librarySystem.getIsSomeoneLoggedIn()) {


        } else {
            System.out.println("You must be logged in to check your loans.");
        }

    }
}
