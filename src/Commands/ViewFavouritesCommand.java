package Commands;

import Librarysystems.UserHandler;

public class ViewFavouritesCommand implements Command {
    UserHandler userHandler;

    public ViewFavouritesCommand() {
        this.userHandler = UserHandler.getInstance();
    }

    @Override
    public void execute() {
        userHandler.getFavourites(userHandler.getLoggedInUser());
    }

}
