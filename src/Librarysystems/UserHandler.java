package Librarysystems;

import Commands.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class UserHandler {

    private static UserHandler instance;
    Scanner scan = new Scanner(System.in);
    private final List<Member> memberList;
    private final List<Admin> adminList;
    private String loggedInUser;
    private Map<String, List<String>> notifications = new HashMap<>();
    private Map<String, List<Book>> favourites;


    private UserHandler() {
        super();
        this.memberList = Member.getMembers();
        this.adminList = Admin.getAdmins();
        List<Book> bookList = Book.getBooks();
        this.favourites = new HashMap<>();
        loadFavouritesFromMapFile(Book.getBooks());
    }

    ////Singleton design mönster
    public static synchronized UserHandler getInstance() {
        if (instance == null) {
            instance = new UserHandler();
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
                    setLoggedInUser(person.getUserName());
                    displayNotifications(person.getUserName());
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

        Member member = (Member) PersonFactory.createPerson(
                "Member", name , yearOfBirth, userName, pincode);

        memberList.add(member);
        System.out.println("\nGreetings " + name);
        return userName;
    }

    public String createAdminAccount() {
        System.out.println("Enter your name: ");
        scan.nextLine();
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
        scan.nextLine();

        try (FileWriter fileWriter = new FileWriter("FileNameAdmin.txt", true)) {
            fileWriter.write(name + "," + yearOfBirth + "," + userName + "," + pincode + "\n");
        } catch (IOException e) {
            System.out.println("Could not create the file");
        }

        Admin admin = (Admin) PersonFactory.createPerson(
                "Admin", name, yearOfBirth, userName, pincode);

        adminList.add(admin);

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

    public void addBookToFavourite(String userName, String ISBN) {
        List<Book> bookList = Book.getBooks(); //get books from file
        boolean bookFound = false;

        System.out.println("Searching for ISBN: " + ISBN);

        try (BufferedReader reader = new BufferedReader(new FileReader("favourites.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(getLoggedInUser()) && parts[1].trim().equals(ISBN.trim())) {
                    System.out.println("This book is already in your favourites.");
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading favourites file: " + e.getMessage());
        }

        for (Book book : bookList) {
            if (book.getISBN().trim().equals(ISBN.trim())) {
                favourites.computeIfAbsent(userName, k -> new ArrayList<>()).add(book);
                saveFavouriteToFile();
                loadFavouritesFromMapFile(Book.getBooks());
                try (FileWriter writer = new FileWriter("favourites.txt", true)) {
                    writer.write( userName + "," + ISBN + "\n");
                } catch (IOException e) {
                    System.out.println("Error saving favorite: " + e.getMessage());
                }
                System.out.println("Book added to favourites: " + book.getTitle());
                bookFound = true;
                break;
            }
        }
        if (!bookFound) {
            System.out.println("No book found with ISBN: " + ISBN);
        }

    }
    public void getFavourites(String userName) {
        List <Book> userFavourites = this.favourites.getOrDefault(userName, new ArrayList<>());
        if (userFavourites != null && !userFavourites.isEmpty()) {
            System.out.println("Your favorite books are: ");
            for (Book book : userFavourites) {
                System.out.println(book.getTitle());
            }
        } else {
            System.out.println("You have no favorite books yet.");
        }
    }

    public void saveFavouriteToFile(){
        try (FileWriter write = new FileWriter("favourites.txt", false)){
            for (Map.Entry<String , List <Book>> entry : favourites.entrySet()) {
                String userName = entry.getKey();
                for(Book book : entry.getValue()){
                    write.write(userName + "," + book.getISBN() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving favourites to file: " + e.getMessage());
        }
    }


    public void loadFavouritesFromMapFile(List <Book> bookList) {
        Map <String, Book> bookMap= new HashMap<>();
        for (Book book : bookList) {
            bookMap.put(book.getISBN(), book);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("favourites.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String userName = parts[0].trim();
                    String isbn = parts[1].trim();
                    Book book = bookMap.get(isbn);
                    if (book != null) {
                        favourites.computeIfAbsent(userName, k -> new ArrayList<>()).add(book);
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteMember(String userName, List<Member> memberListIn) {
        Iterator<Member> iterator = memberListIn.iterator(); // Create an iterator for the list
        while (iterator.hasNext()) {
            Member member = iterator.next(); // Get the next member
            if (member.getUserName().equalsIgnoreCase(userName)) {
                iterator.remove(); // Safely remove the current element
                System.out.println("User " + userName + " deleted successfully");
            }
        }
    }

    public boolean getIsSomeoneLoggedIn() {
        return UserLoginCommand.getLoggedIn() || AdminLoginCommand.getLoggedIn() ||
                CreateAdminAccCommand.getLoggedIn()  || CreateUserAccCommand.getLoggedIn();
    }

    public String getLoggedInUser() {
        String[] usernames = {UserLoginCommand.getLOGGEDIN_USERNAME(), AdminLoginCommand.getLOGGEDIN_USERNAME(),
                CreateAdminAccCommand.getLOGGEDIN_USERNAME(), CreateUserAccCommand.getLOGGEDIN_USERNAME()};

        for (String username : usernames) {
            if (username != null) {
                return username;
            }
        }
        return null;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
        displayNotifications(loggedInUser);
    }

    public void addNotification(String userName, String message){
        notifications.computeIfAbsent(userName, k -> new ArrayList<>()).add(message);
    }

    public void displayNotifications(String userName){
        List<String> userNotifications = notifications.get(userName);
        if (userNotifications != null && !userNotifications.isEmpty()) {
            System.out.println("You have new notifications:");
            System.out.println(userNotifications);
        }
    }
}

