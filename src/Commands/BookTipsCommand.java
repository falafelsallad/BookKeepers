package Commands;

import Librarysystems.Book;
import Librarysystems.UserHandler;

import java.util.Scanner;

public class BookTipsCommand implements Command {
    UserHandler userHandler;

    public BookTipsCommand(Scanner scanner) {
        this.userHandler = UserHandler.getInstance();
    }

    @Override
    public void execute() {
        System.out.println(Book.printInfo(Book.getBooks().get(userHandler.getRandomBook())));
    }
}
