package Commands;
import Librarysystems.*;

import java.util.Scanner;

public class CheckLoansCommand implements Command {
    private UserHandler userHandler;
    private Scanner scanner;

    public CheckLoansCommand(UserHandler userHandler, Scanner scanner) {
        this.userHandler = UserHandler.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        if (userHandler.getIsSomeoneLoggedIn()) {

        } else {
            System.out.println("You must be logged in to check your loans.");
        }

    }
}
