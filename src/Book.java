import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Book {

    private final String bookName;
    private final String author;
    private final String ISBN;
    private final String publisher;
    private final String genre;
    private final int publishYear;
    private int quantity;
    private static final List <String> queue = new ArrayList<>();

    public Book(String bookName, String author, String ISBN, String publisher, String genre, int publishYear, int quantity) {
        this.bookName = bookName;
        this.author = author;
        this.ISBN = ISBN;
        this.publisher = publisher;
        this.genre = genre;
        this.publishYear = publishYear;
        this.quantity = quantity;
    }

    public static List<Book> getBooks() {
        String readInBookName, readInAuthor, readInISBN, readInPublisher, readInGenre, readInYear, readInQuantity;
        int readInYearInt, readInQuantityInt;
        int i = 0;
        List<Book> bookList = new ArrayList<Book>();

        //Uses BufferedReader to read from file
        try (BufferedReader br = new BufferedReader(new FileReader("BookLog"))) {
            // Checks the line is not empty
            String line;
            while ((line = br.readLine()) != null) {
                //Stores Personal-number
                readInBookName = line;
                readInAuthor = br.readLine();
                readInISBN = br.readLine();
                readInPublisher = br.readLine();
                readInGenre = br.readLine();
                readInYear = br.readLine();
                readInQuantity = br.readLine();


                readInYearInt = Integer.parseInt(readInYear);
                readInQuantityInt = Integer.parseInt(readInQuantity);
                Book book = new Book(readInBookName, readInAuthor, readInISBN, readInPublisher, readInGenre, readInYearInt, readInQuantityInt);
                bookList.add(book);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Something went wrong\n Please Try again");
        }
        return bookList;
    }

    public static String printInfo(Book book) {

        return book.bookName + "\n" + book.author + "\n" + book.ISBN + "\n" + book.publisher
                + "\n" + book.genre + "\n" + book.publishYear + "\n" + book.quantity + "\n";
    }

    public static void searchBooks(List<Book> bookList, String searchInput, String userName) {
        List<Book> searchResults = new ArrayList<>();
        String lowerSearchString = searchInput.toLowerCase();               //TODO: Check if the book exists before queue!
        boolean queue = true;

        for (Book book : bookList) {
            if (book.bookName.toLowerCase().contains(lowerSearchString) ||
                    book.author.toLowerCase().contains(lowerSearchString) ||
                    book.ISBN.toLowerCase().contains(lowerSearchString) ||
                    book.publisher.toLowerCase().contains(lowerSearchString) ||
                    book.genre.toLowerCase().contains(lowerSearchString) ||
                    String.valueOf(book.publishYear).contains(lowerSearchString) ||
                    String.valueOf(book.quantity).contains(lowerSearchString)) {
                searchResults.add(book);
            }
            if (book.quantity == 0) {
                queue = false;
                queueBook(book.bookName, userName, printInfo(searchResults.getFirst()));

            }
        }
        if (searchResults.isEmpty()) {
            System.out.println("No books found matching \"" + searchInput + "\".");
        } else if (queue == true) {
            System.out.println("Books matching \"" + searchInput + "\":");
            for (Book result : searchResults) {
                System.out.println(printInfo(result));
            }
        }

    }

    public static void queueBook(String bookName, String userName, String searchResults) {
        System.out.println("Books matching! \"" + bookName + "\":");
        System.out.println(searchResults);
        System.out.println("The book: " + bookName + " is not available.\nWould you like to stand in queue?");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        if (answer.trim().equalsIgnoreCase("yes")){
            if (queue.contains(userName)) {
                System.out.println("You are already in the queue for this book.");
            } else {
                queue.add(userName);
                System.out.println("You have been added to the queue for the book: " + bookName);
            }
        }
    }

    public boolean isAvailable() {
        return this.quantity > 0;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return bookName;
    }

    public void BookQueue (String userName){
        if (!queue.contains(userName)) {
            queue.add(userName);
            System.out.println(userName + " has been added to the queue for the book: " + bookName);
        } else {
            System.out.println(userName + " is already in the queue for this book.");
        }
    }

    public String getISBN() {
        return ISBN;
    }

}
