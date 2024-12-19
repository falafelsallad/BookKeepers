package Commands;
import Librarysystems.*;

import java.util.Scanner;

public class ReturnBookCommand implements Command {
    private UserHandler userHandler;
    private Scanner scanner;

    public ReturnBookCommand(Scanner scanner) {
        this.userHandler = UserHandler.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter the LoanID of the book you want to return: ");
        String loanID = scanner.nextLine().trim();
        if (loanID.isEmpty()) {
            System.out.println("LoanID cannot be empty.");
            return;
        }
        String result = Loan.returnBook(loanID);
        System.out.println(result);
    }
}
