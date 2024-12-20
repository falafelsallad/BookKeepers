import Librarysystems.LibraryInterface;
import Librarysystems.UserHandler;

public class Main {

    public static void main(String[] args) {

        UserHandler userHandler = UserHandler.getInstance();
        LibraryInterface libraryInterface = LibraryInterface.getInstance(userHandler);
    }
}