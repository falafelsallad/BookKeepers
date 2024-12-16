import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class Loan {

    protected String loanID;
    private String ISBN;
    private LocalDate startDate;
    private LocalDate endDate;
    private String userName;

    public Loan(String ISBN, String userName) {
        this.ISBN = ISBN;
        this.userName = userName;
        this.loanID = ISBN + userName + LocalDate.now();
    }

    public Loan(String ISBN, LocalDate startDate, LocalDate endDate, String userName, String loanID) {
        this.ISBN = ISBN;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userName = userName;
        this.loanID = loanID;
    }

    public static String returnBook(String loanID, List<Loan> loanList) {
        if (loanID == null || loanID.isEmpty()) {
            return "Loan does not exist";
        }
        for (Loan loan : loanList) {
            if (loan.getLoanID().trim().equalsIgnoreCase(loanID.trim())) {
                loanList.remove(loan); // Ta bort lånet från listan
                return "The book with Loan ID '" + loanID + "' has been returned.";
            }
        }
        return "No loan found with ID '" + loanID + "'.";
    }

    public static void saveLoansToFile(List<Loan> loanList) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("LoanLog.txt"))) {
            for (Loan loan : loanList) {
                bufferedWriter.write(loan.toString() + "\n");
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readLoanFromFile(List<Loan> loanList) {
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
    }

    public LocalDate calculateDueDate(LocalDate localDate) {
        return localDate.plusDays(30);
    }

    public String loanCreator(String userName, String ISBN, List<Book> bookList) {
        Loan loan = new Loan(ISBN, userName);

        for (Book book : bookList) {
            if (book.getISBN().trim().equals(ISBN.trim()) && book.isAvailable()) {
                book.setQuantity(book.getQuantity() - 1);
                loanID = ISBN + userName + LocalDate.now();
                startDate = LocalDate.now();
                endDate = calculateDueDate(startDate);
                return "Loan created successfully! Loan ID: " + this.loanID + ", Due Date: " + this.endDate;
            }
        }
        return "The book with ISBN " + ISBN + " is not available or does not exist.";
    }

    @Override
    public String toString() {
        return ISBN + "\n" + userName + "\n" + startDate + "\n" + endDate + "\n" + loanID;
    }

    public String getLoanID() {
        return loanID;
    }
}
