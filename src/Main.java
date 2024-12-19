public class Main {

    public static void main(String[] args) {

        LibrarySystem librarySystem = LibrarySystem.getInstance();
        LibraryInterface2 libraryInterface = LibraryInterface2.getInstance(librarySystem);
    }
}