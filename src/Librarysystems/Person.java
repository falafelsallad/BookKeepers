package Librarysystems;

public class Person {
    private final String name;
    private final int yearOfBirth;
    private final String userName;
    private final int password;

    public Person(String name, int yearOfBirth, String userName, int password) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.userName = userName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getUserName() {
        return userName;
    }

    public int getPassword() {
        return password;
    }
}
