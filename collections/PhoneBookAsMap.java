package geekbrains.lesson11;

import java.util.*;

public class PhoneBookAsMap {

    private final Map<String, List<Contact>> contacts;

    public PhoneBookAsMap(){
        contacts = new HashMap<>();
    }

    public void add(String name, Contact c){
        List<Contact> listOfTelNumbers = contacts.get(name);
        if(listOfTelNumbers == null){
            listOfTelNumbers = new ArrayList<>();
        }
        if(!listOfTelNumbers.contains(c)){
            listOfTelNumbers.add(c);
        }
        contacts.put(name, listOfTelNumbers);

    }

    public List<Contact> get(String name){
        return contacts.get(name);

    }

    public static void main(String[] args) {
        PhoneBookAsMap pb1 = new PhoneBookAsMap();
        Contact contact1 = new Contact("Ivanov", 123456);
        Contact contact2 = new Contact("Sergeeva", 1234569876);
        Contact contact3 = new Contact("Ivanov", 12398765);
        Contact contact4 = new Contact("Ivanov", 123456);
        pb1.add(contact1.getName(), contact1);
        pb1.add(contact2.getName(), contact2);
        pb1.add(contact3.getName(), contact3);
        pb1.add(contact4.getName(), contact4);


        System.out.println("поиск по фамилии " + pb1.get("Ivanov"));

        /*for(Map.Entry entry : pb1.contacts.entrySet()){
            System.out.println(entry);
        }*/

    }

}
