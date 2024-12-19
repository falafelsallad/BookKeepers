package Commands;

import Librarysystems.Member;
import Librarysystems.UserHandler;

import java.util.Scanner;

public class FavoritesCommand implements Command {
    private Scanner scanner;
    private UserHandler userHandler;

    public FavoritesCommand(Scanner scanner) {
        this.scanner = scanner;
        this.userHandler = UserHandler.getInstance();
    }

    @Override
    public void execute() {
        System.out.println("Enter the isbn of the book: ");
        String isbn = scanner.nextLine().trim();
        userHandler.addBookToFavourite(userHandler.getLoggedInUser(), isbn);
    }
}
