import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LibrarySystem {

    private static LibrarySystem instance;
    Scanner scan = new Scanner(System.in);
    private final List<Member> memberList;
    private final List<Admin> adminList;

    private LibrarySystem() {
        super();
        this.memberList = Member.getMembers();
        this.adminList = Admin.getAdmins();
        List<Book> bookList = Book.getBooks();
        loadAllFavorites(this.memberList, "favorites.txt", bookList);
    }

    ////Singleton design mönster
    public static synchronized LibrarySystem getInstance() {
        if (instance == null) {
            instance = new LibrarySystem();
        }
        return instance;
    }

    public boolean login(String userName, boolean isAdmin, int attempts) {
        if (attempts >= 3) {
            System.out.println("You have exceeded the number of attempts, try again later");
            return false;
        }

        String username = userName;
        int pincode;
        List<? extends Person> personlist = isAdmin ? adminList : memberList;

        for (Person person : personlist) {
            username = username.trim();
            if (person.getUserName().equalsIgnoreCase(username)) {
                System.out.println("Enter your pincode: ");
                pincode = scan.nextInt();
                if (person.getPassword() == pincode) {
                    System.out.println("Hello " + person.getName());
                    return true;
                } else if (!person.getUserName().equalsIgnoreCase(username)) {
                    System.out.println("Username not found");
                return false;
                } else {
                    System.out.println("Invalid pin code try again");
                    return login(userName, isAdmin, attempts +1);
                }
            }
        }
        System.out.println("Login failed, try again");
        return false;
    }

    public String createUserAccount() {

        System.out.println("Enter your name: ");
        String name = scan.nextLine();

        System.out.println("Year of birth: ");
        int yearOfBirth = scan.nextInt();
        scan.nextLine();

        System.out.println("User name: ");
        String userName = scan.nextLine();

        checkUsernameAvailability(userName, false);
        if (checkUsernameAvailability(userName, false)) {
            System.out.println("This username is occupied, please try another one");
            return createUserAccount();
        }

        System.out.println("Create a pincode 4 digits:");
        int pincode = scan.nextInt();

        try (FileWriter fileWriter = new FileWriter("FileNameUser.txt", true)) {
            fileWriter.write(name + "," + yearOfBirth + "," + userName + "," + pincode + "\n");
        } catch (IOException e) {
            System.out.println("Could not create the file");
        }

        memberList.add(new Member(name, yearOfBirth, userName, pincode));
        System.out.println("`\nGreetings " + name);
        return userName;
    }

    public String createAdminAccount() {
        System.out.println("Enter your name: ");
        String name = scan.nextLine();

        System.out.println("Year of birth: ");
        int yearOfBirth = scan.nextInt();
        scan.nextLine();

        System.out.println("User name: ");
        String userName = scan.nextLine();

        checkUsernameAvailability(userName, true);
        if (checkUsernameAvailability(userName, true)) {
            System.out.println("This username is occupied, please try another one");
            return createAdminAccount();
        }

        System.out.println("Create a pincode 4 digits:");
        int pincode = scan.nextInt();

        try (FileWriter fileWriter = new FileWriter("FileNameAdmin.txt", true)) {
            fileWriter.write(name + "," + yearOfBirth + "," + userName + "," + pincode + "\n");
        } catch (IOException e) {
            System.out.println("Could not create the file");
        }

        adminList.add(new Admin(name, yearOfBirth, userName, pincode));

        System.out.println("Admin " + name + " Salutations!");
        return userName;
    }

    public boolean checkUsernameAvailability(String userName, boolean isAdmin) {
        List<? extends Person> personList = isAdmin? adminList : memberList;
        for (Person person : personList) {
            if (person.getUserName().equalsIgnoreCase(userName)) {
                return true;
            }
        }
        return false;
    }

    public int getRandomBook() {
        Random random = new Random();
        return random.nextInt(50);
    }

    public void updateUserFile(List<Member> memberListIn) {

        try (FileWriter fileWriter = new FileWriter("FileNameUser.txt", false)) { // 'false' overwrites the file

            for (Member member : memberListIn) {
                fileWriter.write(member.getName() + "," + member.getYearOfBirth() + "," + member.getUserName() + "," + member.getPassword() + "\n");
            }
            System.out.println("File updated successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while clearing the file: " + e.getMessage());
        }
    }

    public void loadAllFavorites(List<Member> memberList, String filePath, List<Book> bookList) {
        for (Member member : memberList) {
            member.loadFavoritesFromFile(filePath, bookList);
        }
    }

    public void deleteUser(String userName, List<Member> memberListIn) {
        Iterator<Member> iterator = memberListIn.iterator(); // Create an iterator for the list
        while (iterator.hasNext()) {
            Member member = iterator.next(); // Get the next member
            if (member.getUserName().equalsIgnoreCase(userName)) {
                iterator.remove(); // Safely remove the current element
                System.out.println("User " + userName + " deleted successfully");
            }
        }
    }
}