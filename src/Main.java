import Librarysystems.LibraryInterface;
import Librarysystems.LibraryInterface2;
import Librarysystems.UserHandler;

public class Main {

    public static void main(String[] args) {

        UserHandler userHandler = UserHandler.getInstance();
        LibraryInterface2 libraryInterface = LibraryInterface2.getInstance(userHandler);
    }
}