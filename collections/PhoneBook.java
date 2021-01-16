/*2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
        В этот телефонный справочник с помощью метода add() можно добавлять записи.
        С помощью метода get() искать номер телефона по фамилии.
        Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
        тогда при запросе такой фамилии должны выводиться все телефоны.

        Желательно как можно меньше добавлять своего, чего нет в задании
        (т.е. не надо в телефонную запись добавлять еще дополнительные поля (имя, отчество, адрес),
        делать взаимодействие с пользователем через консоль и т.д.).
        Консоль желательно не использовать (в том числе Scanner),
        тестировать просто из метода main(), прописывая add() и get().*/

package geekbrains.lesson11;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PhoneBook {

    private TreeSet<Contact> contacts;

    public PhoneBook(){
        contacts = new TreeSet<>();
    }

    public void add(Contact c){
        contacts.add(c);
    }


    public List<Contact> get(String name){
        List<Contact> extracted = new ArrayList<>();
        for (Contact contact : contacts) {
            if(contact.getName().equals(name)){
                extracted.add(contact);
            }
        }
        return extracted;
    }

    @Override
    public String toString() {
        return "Все контакты: " + contacts;
    }

    public static void main(String[] args) {

        PhoneBook phoneBook = new PhoneBook();

        Contact contact1 = new Contact("Гаврилова", 89123453);
        Contact contact2 = new Contact( "Иванов", 89123456);
        Contact contact3 = new Contact("Смирнов", 891234587);
        Contact contact4 = new Contact("Сергеева", 89123457);
        Contact contact5 = new Contact("Иванов", 89123410);

        phoneBook.add(contact1);
        phoneBook.add(contact2);
        phoneBook.add(contact3);
        phoneBook.add(contact4);
        phoneBook.add(contact5);


        System.out.println(phoneBook);

        System.out.println("Поиск по фамилии: " + phoneBook.get("Иванов"));
        System.out.println();


    }

}


