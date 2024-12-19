package Commands;
import Librarysystems.*;

public class LibraryInfoCommand implements Command {
    private String libraryName;
    private String libraryLocation;
    private String libraryPhone;
    private String libraryHours;

    public LibraryInfoCommand() {
        this.libraryName = "Library Name: Library";
        this.libraryLocation = "Location: 1234 Library St.";
        this.libraryPhone = "Phone: 123-456-7890";
        this.libraryHours = "Hours: 9am-5pm";
    }

    @Override
    public void execute() {
        System.out.println(libraryName + " " + libraryLocation + " " + libraryPhone + " " + libraryHours);
    }
}
