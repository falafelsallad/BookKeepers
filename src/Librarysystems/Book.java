package Librarysystems;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Book {

    private final String bookName;
    private final String author;
    private final String ISBN;
    private final String publisher;
    private final String genre;
    private final int publishYear;
    private int quantity;
    private final Queue<String> queue = new LinkedList<>();

    public Book(String bookName, String author, String ISBN, String publisher, String genre, int publishYear, int quantity) {
        this.bookName = bookName;
        this.author = author;
        this.ISBN = ISBN;
        this.publisher = publisher;
        this.genre = genre;
        this.publishYear = publishYear;
        this.quantity = quantity;
    }

    public void addToQueue(String userName) {
        queue.add(userName);
    }

    public String removeFromQueue() {
        return queue.poll();
    }

    public Queue<String> getQueue(){
        return queue;
    }


    public static void saveBooksToFile(List<Book> bookList) {
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get("BookLog"))) {
            for (Book book : bookList) {
            writer.write(book.bookName + "\n");
            writer.write(book.author + "\n");
            writer.write(book.ISBN + "\n");
            writer.write(book.publisher + "\n");
            writer.write(book.genre + "\n");
            writer.write(book.publishYear + "\n");
            writer.write(book.quantity + "\n");
            }
        } catch (IOException e){
        throw new RuntimeException("Error saving books to file: " + e.getMessage());
        }
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
                readInISBN = br.readLine().trim();
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

    public static void searchBooks(String searchInput, List<Book> bookList,  String userName) {
        List<Book> searchResults = new ArrayList<>();
        String lowerSearchString = searchInput.toLowerCase();               //TODO: Check if the book exists before queue!
        boolean queue = false;

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

            if (!book.isAvailable() && searchResults.contains(book)) {
                queue = true;
                System.out.println("Books matching! \"" + searchInput + "\":");
                System.out.println(printInfo(searchResults.getFirst()));
                book.addToQueue( userName);
            } else if (book.isAvailable() && searchResults.contains(book)) {
                queue = true;
                System.out.println("Books matching! \"" + searchInput + "\":");
                System.out.println(printInfo(searchResults.getFirst()));
                System.out.println("Would you like to apply for a loan? Enter 'yes' or 'no'.");
                Scanner scan = new Scanner(System.in);
                if (scan.nextLine().trim().equalsIgnoreCase("yes")){
                    Loan.applyForLoan(userName, book.getISBN(), bookList);
                    return;
                }
                else System.out.println("Returning to main menu.");
            }
        }
        if (searchResults.isEmpty()) {
            System.out.println("No books found matching \"" + searchInput + "\".");
        } else if (!queue) {
            System.out.println("Book matching \"" + searchInput + "\":");
            for (Book result : searchResults) {
                System.out.println(printInfo(result));
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
        saveBooksToFile(Book.getBooks());
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
