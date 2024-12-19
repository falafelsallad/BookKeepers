import java.util.List;
import java.util.Scanner;

public class SearchCommand implements Command{

    private LibrarySystem librarySystem;
    private Scanner scanner;
    private List<Book> bookList = Book.getBooks();
    private String LOGGEDIN_USERNAME;

    public SearchCommand(LibrarySystem librarySystem, Scanner scanner) {
        this.librarySystem = LibrarySystem.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter a search term: ");
        String searchTerm = scanner.nextLine().trim();
        Book.searchBooks(bookList ,searchTerm, LOGGEDIN_USERNAME);
    }
}
