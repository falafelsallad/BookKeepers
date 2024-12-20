package Librarysystems;

public class PersonFactory {

    public static Person createPerson(String type, String name, int yearOfBirth, String userName, int password){
        if(type.equalsIgnoreCase("Member")) {
            return new Member(name, yearOfBirth, userName, password);
        }else if (type.equalsIgnoreCase("Admin")) {
            return new Admin(name, yearOfBirth, userName, password);
        }
        throw new IllegalArgumentException("Unknown person type" + type);
    }

}
