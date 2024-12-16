import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends Person {
    Scanner scan = new Scanner(System.in);

    public Admin(String name, int yearOfBirth, String userName, int password) {
        super(name, yearOfBirth, userName, password);
    }

    public static List<Admin> getAdmins() {
        String readInName, readInYearOfBirth, readInUserName, readInPassword;
        int readInYearOfBirthInt, readInPasswordInt;
        int i = 0;
        List<Admin> adminList = new ArrayList<>();

        //Uses BufferedReader to read from file
        try (BufferedReader br = new BufferedReader(new FileReader("FileNameAdmin.txt"))) {
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
                    Admin admin = new Admin(readInName, readInYearOfBirthInt, readInUserName, readInPasswordInt);
                    adminList.add(admin);
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
        return adminList;
    }
}
