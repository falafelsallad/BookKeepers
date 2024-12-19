package Commands;
import Librarysystems.*;

import java.util.List;
import java.util.Scanner;

public class SearchCommand implements Command {

    private UserHandler userHandler;
    private Scanner scanner;
    private List<Book> bookList = Book.getBooks();
    private String LOGGEDIN_USERNAME;

    public SearchCommand(UserHandler userHandler, Scanner scanner) {
        this.userHandler = UserHandler.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter a search term: ");
        String searchTerm = scanner.nextLine().trim();
        Book.searchBooks(searchTerm, bookList, userHandler.getLoggedInUser());
    }
}
