package Librarysystems;
import Commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LibraryInterface2 {

    private static LibraryInterface2 instance;
    private UserHandler userHandler;
    private Map<Integer, Command> loginCommands;
    private Map<Integer, Command> userCommands;
    private Map<Integer, Command> adminCommands;
    private final String menuOptionsLogin = "\n1. Login\n2. Sign up!!\n3. Admin Login\n4. Information";

    private final String userMenuOptions = "\n1. Search/Check quantity \n2. Borrow a book \n3. Return a book " +
            "\n4. Book Tips \n5. Mark a favorite book\n6. View Favourites\n7. Check Loans\n8. View Notifications\n9. Exit";

    private final String adminMenuOptions = "\n1.Check Loans\n2.Check Members\n3.Delete Member\n4.Create new Admin Account\n5.Exit";

    private LibraryInterface2(UserHandler userHandler){
        this.userHandler = UserHandler.getInstance();
        initializeCommands();
        runInterface();

    }
    private void initializeCommands() {
    Scanner scanner = new Scanner(System.in);

    loginCommands = new HashMap<>();
    loginCommands.put(1, new UserLoginCommand(scanner));
    loginCommands.put(2, new CreateUserAccCommand(scanner));
    loginCommands.put(3, new AdminLoginCommand(scanner));
    loginCommands.put(4, new LibraryInfoCommand());

    userCommands= new HashMap<>();
    userCommands.put(1, new SearchCommand(scanner));
    userCommands.put(2, new BorrowCommand(scanner));
    userCommands.put(3, new ReturnBookCommand(scanner));
    userCommands.put(4, new BookTipsCommand(scanner));
    userCommands.put(5, new FavoritesCommand(scanner));
    userCommands.put(6, new ViewFavouritesCommand());
    userCommands.put(7, new CheckYourLoansCommand(scanner));
    userCommands.put(8, new ViewNotifcationCommand());
    userCommands.put(9, new ExitCommand());



    adminCommands = new HashMap<>();
    adminCommands.put(1, new CheckLoanLog(scanner));
    adminCommands.put(2, new CheckMembersCommand(scanner));
    adminCommands.put(3, new DeleteMemberCommand(scanner));
    adminCommands.put(4, new CreateAdminAccCommand(scanner));
    adminCommands.put(5, new ExitCommand());

    }

    private void runInterface(){
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;
        boolean adminLoggedIn = false;

        while (!loggedIn && !adminLoggedIn) {
            System.out.println(menuOptionsLogin);
            int choice = getValidChoice(scanner, 1, 4);

            Command command = loginCommands.get(choice);
            if (command != null) {
                command.execute();

                if (command instanceof UserLoginCommand) {
                    loggedIn = UserLoginCommand.getLoggedIn();
                }
                if (command instanceof AdminLoginCommand) {
                    adminLoggedIn = AdminLoginCommand.getLoggedIn();
                }
                if (command instanceof CreateUserAccCommand) {
                    loggedIn = CreateUserAccCommand.getLoggedIn();
                }
                if (command instanceof CreateAdminAccCommand) {
                    adminLoggedIn = CreateAdminAccCommand.getLoggedIn();
                }

            } else {
                System.out.println("Invalid choice");
            }
        }

        if (loggedIn) {
            while (loggedIn) {
                System.out.println(userMenuOptions);
                int choice = getValidChoice(scanner, 1, 9);

                Command command = userCommands.get(choice);
                if (command != null) {
                    command.execute();
                    if (choice == 9) {
                        loggedIn = false;
                    }
                } else {
                    System.out.println("Invalid choice");
                }

            }
        }

        if(adminLoggedIn) {
            while (adminLoggedIn){
                System.out.println(adminMenuOptions);
                int choice = getValidChoice(scanner, 1, 5);

                Command command = adminCommands.get(choice);
                if (command != null) {
                    command.execute();
                    if (choice == 5){
                        adminLoggedIn = false;
                    }
                } else {
                    System.out.println("Invalid choice");
                }
            }
        }
    }

    private int getValidChoice(Scanner scanner, int min, int max) {
        int choice;
        while (true){
            System.out.println("Enter your choice: ");

            if (scanner.hasNextInt()){
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                if (choice >= min && choice <= max) break;
                else System.out.println("Invalid choice. Enter a number between " + min + " and " + max);
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return choice;
    }


    public static synchronized LibraryInterface2 getInstance(UserHandler userHandler) {
        if (instance == null) {
            instance = new LibraryInterface2(userHandler);
        }
        return instance;
    }
}
