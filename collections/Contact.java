package geekbrains.lesson11;

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


}
