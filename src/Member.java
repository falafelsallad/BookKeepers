import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Member extends Person {
    private final List<Book> favoriteBooks = new ArrayList<>(); //List for favorite books.

    public Member(String name, int yearOfBirth, String userName, int password) {
        super(name, yearOfBirth, userName, password);
        int numberOfLoans = 0;
    }

    public void addFavoriteBookFromLog(String ISBN, String filePath) {
        List<Book> bookList = Book.getBooks(); //get books from file
        boolean bookFound = false;

        System.out.println("Searching for ISBN: " + ISBN);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(this.getUserName()) && parts[1].trim().equals(ISBN.trim())) {
                    System.out.println("This book is already in your favorites.");
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading favorites file: " + e.getMessage());
        }

        for (Book book : bookList) {
            if (book.getISBN().trim().equals(ISBN.trim())) {
                favoriteBooks.add(book);
                try (FileWriter writer = new FileWriter(filePath, true)) {
                    writer.write(this.getUserName() + "," + ISBN + "\n");
                } catch (IOException e) {
                    System.out.println("Error saving favorite: " + e.getMessage());
                }

                System.out.println("Book added to favorites: " + book.getTitle());
                bookFound = true;
                break;
            }
        }
        if (!bookFound) {
            System.out.println("No book found with ISBN: " + ISBN);
        }

    }

    public void loadFavoritesFromFile(String filePath, List<Book> bookList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(this.getUserName())) {
                    String isbn = parts[1].trim();
                    for (Book book : bookList) {
                        if (book.getISBN().equals(isbn)) {
                            if (!favoriteBooks.contains(book)) {
                                favoriteBooks.add(book);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading favorites: " + e.getMessage());
        }
    }

    public List<Book> getFavoriteBooks() {
        return favoriteBooks;
    }

    public static List<Member> getMembers() {
        String readInName, readInYearOfBirth, readInUserName, readInPassword;
        int readInYearOfBirthInt, readInPasswordInt;
        int i = 0;
        List<Member> memberList = new ArrayList<Member>();

        //Uses BufferedReader to read from file
        try (BufferedReader br = new BufferedReader(new FileReader("FileNameUser.txt"))) {
            // Checks the line is not empty
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into parts based on the comma separator
                String[] parts = line.split(",");
                if (parts.length == 4) { // Ensure the line has exactly 4 components
                    // Parse values from the line
                    readInName = parts[0].trim(); // Trim to remove any extra spaces
                    readInYearOfBirth = parts[1].trim();
                    readInUserName = parts[2].trim();
                    readInPassword = parts[3].trim();

                    // Convert numerical fields to integers
                    readInYearOfBirthInt = Integer.parseInt(readInYearOfBirth);
                    readInPasswordInt = Integer.parseInt(readInPassword);

                    // Create a Member object and add it to the list
                    Member member = new Member(readInName, readInYearOfBirthInt, readInUserName, readInPasswordInt);
                    memberList.add(member);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found");
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Something went wrong\n Please Try again");
        }
        return memberList;
    }
}
