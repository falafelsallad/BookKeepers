package Commands;

import Librarysystems.Loan;
import Librarysystems.UserHandler;

import java.util.Scanner;

public class CheckLoanLog implements Command {
    private UserHandler userHandler;
    private Scanner scanner;

    public CheckLoanLog(Scanner scanner) {
        this.userHandler = UserHandler.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println(Loan.loanListToString());

    }
}
