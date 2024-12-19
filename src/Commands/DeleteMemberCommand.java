package Commands;

import Librarysystems.Member;
import Librarysystems.UserHandler;

import java.util.Scanner;

public class DeleteMemberCommand implements Command {
    private UserHandler userHandler;
    private Scanner scanner;

    public DeleteMemberCommand(Scanner scanner) {
        this.userHandler = UserHandler.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter the username of the member you want to delete: ");
        String username = scanner.nextLine().trim();
        userHandler.deleteMember(username, Member.getMembers());
        userHandler.updateUserFile(Member.getMembers());

    }
}
