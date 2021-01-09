package geekbrains.lesson11;

import java.util.*;

public class PhoneBookAsMap {

    private final Map<String, List<Integer>> contacts;

    public PhoneBookAsMap(){
        contacts = new HashMap<>();
    }

    public void add(String name, Integer tel){
        List<Integer> listOfTelNumbers = contacts.get(name);
        if(listOfTelNumbers == null){
            listOfTelNumbers = new ArrayList<>();
        }
        if(!listOfTelNumbers.contains(tel)){
            listOfTelNumbers.add(tel);
        }
        contacts.put(name, listOfTelNumbers);

    }

    public List<Integer> get(String name){
        return contacts.get(name);

    }

    public static void main(String[] args) {
        PhoneBookAsMap pb1 = new PhoneBookAsMap();
        pb1.add("Ivanov", 123456);
        pb1.add("Smirnov", 1234569876);
        pb1.add("Sergeeva", 1234569876);
        pb1.add("Ivanov", 12398765);
        pb1.add("Ivanov", 123456);

        System.out.println(pb1.get("Ivanov"));

        /*for(Map.Entry entry : pb1.contacts.entrySet()){
            System.out.println(entry);
        }*/

    }

}
