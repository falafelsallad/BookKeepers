package Commands;

import Librarysystems.UserHandler;

public class ViewNotifcationCommand implements Command {
    private UserHandler userHandler;

    public ViewNotifcationCommand() {
        this.userHandler = UserHandler.getInstance();
    }

    @Override
    public void execute() {
        userHandler.displayNotifications(userHandler.getLoggedInUser());
        System.out.println("You have no notifications.");
    }
}
