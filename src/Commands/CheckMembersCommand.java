package Commands;

import Librarysystems.Loan;
import Librarysystems.Member;
import Librarysystems.UserHandler;

import java.util.Scanner;

public class CheckMembersCommand implements Command {
    private UserHandler userHandler;

    public CheckMembersCommand(Scanner scanner) {
        this.userHandler = UserHandler.getInstance();
    }

    @Override
    public void execute() {
        System.out.println("Members: " + Member.memberListToString());
    }
}
