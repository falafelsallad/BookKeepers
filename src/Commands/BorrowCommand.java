package Commands;
import Librarysystems.*;

import java.util.List;
import java.util.Scanner;

public class BorrowCommand implements Command {
    private UserHandler userHandler;
    private Scanner scanner;
    private List<Book> bookList = Book.getBooks();

    public BorrowCommand(UserHandler userHandler, Scanner scanner) {
        this.userHandler = UserHandler.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Choose a book by entering its ISBN: \n (or enter 'search' to search for a book)");
        String userInput = scanner.nextLine().trim();
        if (userInput.equalsIgnoreCase("search")) {
            new SearchCommand(userHandler, scanner).execute();
        } else if (userInput.trim().isEmpty() || !userInput.matches("[0-9]+") || userInput.length() != 13) {
            System.out.println("Invalid input, ISBN should be 13 digits long try again.");
            return;
        }
        Loan.applyForLoan(userHandler.getLoggedInUser(), userInput, bookList);
    }
}
