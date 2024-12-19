package Commands;
import Librarysystems.*;

import java.util.Scanner;

public class CheckYourLoansCommand implements Command {
    private UserHandler userHandler;
    private Scanner scanner;

    public CheckYourLoansCommand(Scanner scanner) {
        this.userHandler = UserHandler.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
       Loan.getUserLoans(userHandler.getLoggedInUser());

    }
}
