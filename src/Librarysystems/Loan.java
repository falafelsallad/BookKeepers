package Librarysystems;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Loan {

    protected String loanID;
    private final String ISBN;
    private LocalDate startDate;
    private LocalDate endDate;
    private String userName;
    private static List<Loan> loanList = new ArrayList<>();
    private static UserHandler userHandler;

    static {
        userHandler = UserHandler.getInstance();
        loanList = readLoanFromFile(loanList);
    }

    public Loan(String ISBN, LocalDate startDate, LocalDate endDate, String userName, String loanID) {
        this.ISBN = ISBN;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userName = userName;
        this.loanID = generateLoanID(ISBN, userName);
        userHandler = UserHandler.getInstance();
    }

    public static String returnBook(String loanID) {
        if (loanID == null || loanID.isEmpty()) {
            return "Loan does not exist";
        }
        for (Loan loan : loanList) {
            if (loan.getLoanID().trim().equalsIgnoreCase(loanID.trim())) {
                loanList.remove(loan);
                saveLoansToFile(loanList);
                for (Book book : Book.getBooks()) {
                    if (book.getISBN().equals(loan.getISBN())) {
                        book.setQuantity(book.getQuantity() + 1);
                        if(!book.getQueue().isEmpty()){
                            String nextUser = book.removeFromQueue();
                            notifyUser(nextUser, book);
                        }
                    }
                }
                return "The book with Loan ID '" + loanID + "' has been returned.";

            }
        }
        return "No loan found with ID '" + loanID + "'.";
    }

    public static void notifyUser(String userName, Book book) {
        if (userHandler.getLoggedInUser().equalsIgnoreCase(userName)) {
            String message = "Notification: " + userName + ", the book '" + book.getTitle() + "' is now available.";
            userHandler.addNotification(userName, message);
        }
    }

    private String getISBN() {
        return ISBN;
    }

    public static void saveLoansToFile(List<Loan> loanList) {
        try (FileWriter fileWriter = new FileWriter("LoanLog.txt", false)){
            for (Loan loan : loanList) {
                fileWriter.write(loan.toString() + "\n");
            }
            System.out.println("Loan list updated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while clearing the file: " + e.getMessage());        }
    }

    public static void applyForLoan(String userName, String ISBN, List<Book> bookList) {
        if (ISBN.matches("[0-9]+") && (ISBN == null || ISBN.length() != 13)) {
            System.out.println("Invalid input, ISBN should be 13 digits long try again.");
        } else {
            for (Book book : bookList) {
                if (book.getISBN().trim().equals(ISBN.trim()) && book.isAvailable()) {
                    if (book.isAvailable()){
                    book.setQuantity(book.getQuantity() - 1);
                    LocalDate startDate = LocalDate.now();
                    LocalDate endDate = calculateDueDate(startDate);

                    Loan loan = new Loan(ISBN, startDate, endDate, userName, generateLoanID(ISBN, userName));
                    System.out.println("Loan created successfully! Loan ID: " + generateLoanID(ISBN, userName) + ", Due Date: " + endDate);

                    loanList.add(loan);
                    saveLoansToFile(loanList);
                    Book.saveBooksToFile(bookList);
                } else {
                    System.out.println("The book is not available. Would you like to stand in queue?");
                    Scanner scan = new Scanner(System.in);
                    if (scan.nextLine().trim().equalsIgnoreCase("yes")){
                        book.addToQueue(userName);
                    }
                    }
                    return;
                }
            }
        }
    }


    public static List<Loan> readLoanFromFile(List<Loan> loanList) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("LoanLog.txt"))) {
            while (true) {
                String ISBN = bufferedReader.readLine();
                if (ISBN == null) {
                    break;
                }
                String userName = bufferedReader.readLine();
                LocalDate startDate = LocalDate.parse(bufferedReader.readLine());
                LocalDate endDate = LocalDate.parse(bufferedReader.readLine());
                String loanID = bufferedReader.readLine();

                Loan loan = new Loan(ISBN, startDate, endDate, userName, loanID);

                loanList.add(loan);
            }
        } catch (IOException e) {
            System.out.println("There is no borrowed book in the list yet.");
        }
        return Loan.loanList;
    }

    public static List<Loan> getLoanList() {
        return Loan.loanList;
    }

    public static String loanListToString() {
        StringBuilder sb = new StringBuilder();
        for (Loan loan : loanList) {
            sb.append(loan.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void getUserLoans(String userName) {
        for (Loan loan : loanList) {
            if (loan.userName.equalsIgnoreCase(userName)) {
                System.out.println(loan);
            }
        }
    }

    public static LocalDate calculateDueDate(LocalDate localDate) {
        return localDate.plusDays(30);
    }

    public static String generateLoanID(String ISBN, String username) {
        return ISBN + username.trim() + LocalDate.now();
    }

    @Override
    public String toString() {
        return ISBN + "\n" + userName + "\n" + startDate + "\n" + endDate + "\n" + loanID + "\n";
    }

    public String getLoanID() {
        return loanID;
    }
}
