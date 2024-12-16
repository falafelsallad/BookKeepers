public class Main {

    public static void main(String[] args) {

        LibrarySystem librarySystem = LibrarySystem.getInstance();
        LibraryInterface libraryInterface = LibraryInterface.getInstance(librarySystem);
    }
}