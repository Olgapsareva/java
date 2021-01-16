package geekbrains.lesson11;

import java.util.Objects;

public class Contact implements Comparable<Contact>{
    private String name;
    private int phoneNumber;

    public Contact(String name, int phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }


    public int getPhoneNumber() {
        return phoneNumber;
    }


    public void printInfo(){
        System.out.println(this);
    }

    @Override
    public String toString() {
        return " Фамилия: " + name + " Телефон: " + phoneNumber;
    }

    @Override
    public int compareTo(Contact c) {
        int result = name.compareTo(c.getName());
        if(result==0){
            return phoneNumber - c.getPhoneNumber();
        }
            return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return phoneNumber == contact.phoneNumber &&
                name.equals(contact.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber);
    }
}
