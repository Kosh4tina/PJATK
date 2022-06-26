import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import functions.ItemsType;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Osiedla> osidlas = new ArrayList<>();
        ArrayList<Block> blocks = new ArrayList<>();
        ArrayList<Mieszkanie> mieszkanies = new ArrayList<>();
        ArrayList<MiejsceParkingowe> miejsceParkingowes = new ArrayList<>();
        ArrayList<Person> persons = new ArrayList<>();
        ArrayList<Items> items = new ArrayList<>();

        Osiedla osiedla1 = new Osiedla(1, "Krasno-kwietnie");
        osidlas.add(osiedla1);

        Block block1 = new Block(1, 1);
        blocks.add(block1);

        osiedla1.addBlock(block1);

        Mieszkanie mieszkanie1 = new Mieszkanie(100);
        Mieszkanie mieszkanie2 = new Mieszkanie(5, 5, 4);
        Mieszkanie mieszkanie3 = new Mieszkanie(80);
        Mieszkanie mieszkanie4 = new Mieszkanie(140);
        Mieszkanie mieszkanie5 = new Mieszkanie(30);
        Mieszkanie mieszkanie6 = new Mieszkanie(60);
        Mieszkanie mieszkanie7 = new Mieszkanie(5, 7, 2);
        Mieszkanie mieszkanie8 = new Mieszkanie(8, 5, 2.5);
        Mieszkanie mieszkanie9 = new Mieszkanie(4, 5, 1.5);
        Mieszkanie mieszkanie10 = new Mieszkanie(3, 5, 2.2);
        mieszkanies.add(mieszkanie1);
        mieszkanies.add(mieszkanie2);
        mieszkanies.add(mieszkanie3);
        mieszkanies.add(mieszkanie4);
        mieszkanies.add(mieszkanie5);
        mieszkanies.add(mieszkanie6);
        mieszkanies.add(mieszkanie7);
        mieszkanies.add(mieszkanie8);
        mieszkanies.add(mieszkanie9);
        mieszkanies.add(mieszkanie10);

        block1.addMieszkanie(mieszkanie1);
        block1.addMieszkanie(mieszkanie2);
        block1.addMieszkanie(mieszkanie3);
        block1.addMieszkanie(mieszkanie4);
        block1.addMieszkanie(mieszkanie5);
        block1.addMieszkanie(mieszkanie6);
        block1.addMieszkanie(mieszkanie7);
        block1.addMieszkanie(mieszkanie8);
        block1.addMieszkanie(mieszkanie9);
        block1.addMieszkanie(mieszkanie10);

        MiejsceParkingowe miejsceParkingowe1 = new MiejsceParkingowe(100);
        MiejsceParkingowe miejsceParkingowe2 = new MiejsceParkingowe(100);
        MiejsceParkingowe miejsceParkingowe3 = new MiejsceParkingowe(100);
        MiejsceParkingowe miejsceParkingowe4 = new MiejsceParkingowe(100);
        MiejsceParkingowe miejsceParkingowe5 = new MiejsceParkingowe(100);
        MiejsceParkingowe miejsceParkingowe6 = new MiejsceParkingowe(100);
        MiejsceParkingowe miejsceParkingowe7 = new MiejsceParkingowe(100);
        MiejsceParkingowe miejsceParkingowe8 = new MiejsceParkingowe(100);
        MiejsceParkingowe miejsceParkingowe9 = new MiejsceParkingowe(100);
        MiejsceParkingowe miejsceParkingowe10 = new MiejsceParkingowe(100);
        miejsceParkingowes.add(miejsceParkingowe1);
        miejsceParkingowes.add(miejsceParkingowe2);
        miejsceParkingowes.add(miejsceParkingowe3);
        miejsceParkingowes.add(miejsceParkingowe4);
        miejsceParkingowes.add(miejsceParkingowe5);
        miejsceParkingowes.add(miejsceParkingowe6);
        miejsceParkingowes.add(miejsceParkingowe7);
        miejsceParkingowes.add(miejsceParkingowe8);
        miejsceParkingowes.add(miejsceParkingowe9);
        miejsceParkingowes.add(miejsceParkingowe10);

        block1.addMiesce(miejsceParkingowe1);
        block1.addMiesce(miejsceParkingowe2);
        block1.addMiesce(miejsceParkingowe3);
        block1.addMiesce(miejsceParkingowe4);
        block1.addMiesce(miejsceParkingowe5);
        block1.addMiesce(miejsceParkingowe6);
        block1.addMiesce(miejsceParkingowe7);
        block1.addMiesce(miejsceParkingowe8);
        block1.addMiesce(miejsceParkingowe9);
        block1.addMiesce(miejsceParkingowe10);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = dateFormat.parse("1978/12/23");

        Person person1 = new Person("Marry", "Spilberg", 12311, "Address, 12311", date);
        Person person2 = new Person("David", "Mazer", 12322, "Address, 12322", date);
        Person person3 = new Person("Jack", "Danials", 12333, "Address, 12333", date);
        Person person4 = new Person("Dany", "Boory", 12344, "Address, 12344", date);
        Person person5 = new Person("Kim", "Kardi", 12355, "Address, 12355", date);
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        persons.add(person4);
        persons.add(person1);
        osiedla1.addPerson(person1);
        osiedla1.addPerson(person2);
        osiedla1.addPerson(person3);
        osiedla1.addPerson(person4);
        osiedla1.addPerson(person5);

        Items item1 = new Items(person1, ItemsType.SAMOCHOD_MIEJSKI, "Reno Logan", 2, 3, 4,  "Car");
        item1.addCH("Pojemnosc cilnika", "1600 cm3");
        item1.addCH("Modyfikacja", "1.6 i 16V / 102 hp (76kW)");
        Items item2 = new Items(person1, ItemsType.RANDOM_ITEM, "Box", 6, "Random Item");
        Items item3 = new Items(person1, ItemsType.AMFIBIA, "Amfibia Jakas", 3, "Amfibia");
        Items item4 = new Items(person1, ItemsType.SAMOCHOD_TERENOWY, "Land Cruser", 40, "Car");
        item4.addCH("Pojemnosc cilnika", "1900 cm3");
        item4.addCH("Modyfikacja", "1.6 i 16V / 102 hp (96kW)");
        item4.addCH("Przejezdnosc", "Wysoka");
        Items item5 = new Items(person2, ItemsType.SAMOCHOD_MIEJSKI, "Tayota", 30, "Car");
        Items item6 = new Items(person2, ItemsType.RANDOM_ITEM, "Box Niam", 5, "Random Item");
        Items item7 = new Items(person2, ItemsType.AMFIBIA, "Jakas Amfibia", 3, "Amfibia");
        Items item8 = new Items(person3, ItemsType.LODZ, "Szybka lodz", 15, "Lodz");
        Items item9 = new Items(person4, ItemsType.MOTOCYKL, "Subaru", 12, "Motocykl");
        Items item10 = new Items(person5, ItemsType.RANDOM_ITEM, "Box Meam", 2, "Random Item");
        Items item11 = new Items(person3, ItemsType.RANDOM_ITEM, "Box Nemiam", 7, "Random Item");
        Items item12 = new Items(person4, ItemsType.SAMOCHOD_MIEJSKI, "Maybach", 2, 3, 4, "Car");
        Items item13 = new Items(person5, ItemsType.LODZ, "Szybkaa lodz", 15, "Lodz");
        Items item14 = new Items(person2, ItemsType.SAMOCHOD_TERENOWY, "Cruiser Prado", 40, "Car");
        item14.addCH("Przejezdnosc", "Wysoka");
        Items item15 = new Items(person3, ItemsType.MOTOCYKL, "Subaro X", 12, "Motocykl");
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item6);
        items.add(item7);
        items.add(item8);
        items.add(item9);
        items.add(item10);
        items.add(item11);
        items.add(item12);
        items.add(item13);
        items.add(item14);
        items.add(item15);

        //LOGIC FOR START
        person1.addMieszkanie(mieszkanie1);
        person1.addMieszkanie(mieszkanie2);
        person1.addMieszkanie(mieszkanie3);
        person1.addMieszkanie(mieszkanie4);
        person2.addMieszkanie(mieszkanie1);
        person3.addMieszkanie(mieszkanie1);
        person5.addMieszkanie(mieszkanie5);
        person5.addMieszkanie(mieszkanie6);
        person3.addMieszkanie(mieszkanie7);
        person4.addMieszkanie(mieszkanie8);
        person4.addMieszkanie(mieszkanie9);
        person2.addMieszkanie(mieszkanie3);

        person1.addMiesceParkingowe(miejsceParkingowe1);
        person1.addMiesceParkingowe(miejsceParkingowe2);
        person1.addMiesceParkingowe(miejsceParkingowe3);
        person1.addMiesceParkingowe(miejsceParkingowe4);
        person2.addMiesceParkingowe(miejsceParkingowe1);
        person3.addMiesceParkingowe(miejsceParkingowe1);
        person5.addMiesceParkingowe(miejsceParkingowe5);
        person5.addMiesceParkingowe(miejsceParkingowe6);
        person3.addMiesceParkingowe(miejsceParkingowe7);
        person4.addMiesceParkingowe(miejsceParkingowe8);
        person4.addMiesceParkingowe(miejsceParkingowe9);
        person2.addMiesceParkingowe(miejsceParkingowe3);

        mieszkanie1.addItem(item2);
        miejsceParkingowe1.addItem(item1);
        miejsceParkingowe1.addItem(item3);
        miejsceParkingowe1.addItem(item4);

        person2.addFileM(mieszkanie10, "Naruszenie");
        person2.addFileM(mieszkanie3, "Naruszenie");
        person2.addFileM(mieszkanie4, "Naruszenie");

        //START PROGRAM
        int cheker1 = 0;
        int cheker2 = 0;
        do {
            System.out.println("=============================================================");
            System.out.println("1. Authorization \n2. Simulate the program with threads \n0. Exit and save all data");
            String input1 = scanner.nextLine();
            switch (input1) {
                case "1" -> {
                    System.out.println("Enter your PESEL for authorization ");
                    String pesel = scanner.nextLine();
                    Person newPerson = null;
                    for (Person person : persons) {
                        if ( Integer.parseInt(pesel) == person.getPesel()) {
                            newPerson = person;
                            cheker2 = 0;
                        }
                    }

                    if (newPerson == null) {
                        System.out.println("Person not found");
                        cheker1++;
                        break;
                    }

                    do {
                        System.out.println("=============================================================");
                        System.out.println("Welcome " + newPerson.getImie());
                        System.out.println("\n1. Show your apartments \n2. Show free apartments for purchase \n3. Show available sharing apartments \n4. Show your property \n5. Show your parking spots  \n0. Go back");
                        String input2 = scanner.nextLine();
                        switch (input2) {
                            case "1" -> {
                                System.out.println(newPerson);
                                System.out.println(newPerson.showMieszkania());
                                System.out.println("Choose an apartment for interaction, 0 to back\n");
                                if (newPerson.countMieszkania() != 0) {
                                    String input5 = scanner.nextLine();
                                    if (Integer.parseInt(input5) != 0) {
                                        if(Integer.parseInt(input5) <= mieszkanies.size() && Integer.parseInt(input5) >= 1){
                                            Mieszkanie currentApp = mieszkanies.get(Integer.parseInt(input5) - 1);
                                            if(currentApp.isBuy(newPerson)){
                                                System.out.println(currentApp);
                                                System.out.println("1. Extend the apartment for 1 month (Only available to the owner)\n2. Delete apartment \n3. Show all residents of this apartments\n4. Show all items in the apartment\n5. Add an item from inventory to the apartment\n6. Remove item from apartment\n0. Back");
                                                String input6 = scanner.nextLine();
                                                switch (input6) {
                                                    case "1" -> {
                                                        if(currentApp.isNajemca(newPerson)){
                                                            currentApp.addSomeDays(30);
                                                            System.out.println("Renewal of apartment number " + currentApp.getId() + " for 1 month");
                                                        }else{
                                                            System.out.println("Only the owner can renew the apartment !");
                                                        }
                                                        break;
                                                    }
                                                    case "2" -> {
                                                        MiejsceParkingowe currentMejsce = miejsceParkingowes.get(Integer.parseInt(input5) - 1);
                                                        if (currentMejsce.isBuy(newPerson)) {
                                                            newPerson.removeMiesce(currentMejsce);
                                                        }
                                                        newPerson.removeMieszkanie(currentApp);

                                                        System.out.println("Apartment removed ");
                                                        break;
                                                    }
                                                    case "3" -> {
                                                        System.out.println(currentApp.showAllPersons());
                                                        break;
                                                    }
                                                    case "4" -> {
                                                        System.out.println(currentApp.showAllItems());
                                                        break;
                                                    }
                                                    case "5" -> {
                                                        if (newPerson.countItems() > 0) {
                                                            System.out.println(newPerson.getItems());
                                                            System.out.println("Select an item to add, 0 to back: ");
                                                            Items newItem;
                                                            String itemid = scanner.nextLine();
                                                            if (Integer.parseInt(itemid) != 0) {
                                                                if (newPerson.getItemsArray().contains(newPerson.getItemsArray().get(Integer.parseInt(itemid) - 1))) {
                                                                    newItem = newPerson.getItemsArray().get(Integer.parseInt(itemid) - 1);
                                                                } else {
                                                                    System.out.println("There is no such item");
                                                                    break;
                                                                }
                                                                if (newItem.position == null) {
                                                                    if (newItem.typ == ItemsType.RANDOM_ITEM) {
                                                                        if(currentApp.freeRozmiar > newItem.rozmiar){
                                                                            currentApp.addItem(newItem);
                                                                            System.out.println("Item " + newItem.getNazwa() + " successfully added to apartment number " + currentApp.getId());
                                                                        }else{
                                                                            currentApp.addItem(newItem);
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        System.out.println("You cannot add appliances to the apartment, only on the parking!");
                                                                        break;
                                                                    }
                                                                } else {
                                                                    System.out.println("The item is already in " + newItem.getPosition());
                                                                    break;
                                                                }
                                                            }
                                                        } else {
                                                            System.out.println("You have no items to add!");
                                                        }
                                                        break;
                                                    }
                                                    case "6" -> {
                                                        if (currentApp.countItems() > 0) {
                                                            System.out.println(currentApp.showAllItems());
                                                            System.out.println("Select an item to remove, 0 to back \n");
                                                            String input7 = scanner.nextLine();
                                                            if (Integer.parseInt(input7) != 0) {
                                                                if (Integer.parseInt(input7) <= currentApp.countItems() && Integer.parseInt(input7) > 0) {
                                                                    if (currentApp.getItems().get(Integer.parseInt(input7) - 1).wlascicel == newPerson) {
                                                                        currentApp.removeItem(currentApp.getItems().get(Integer.parseInt(input7) - 1));
                                                                        System.out.println("Item deleted!");
                                                                    } else {
                                                                        System.out.println("Do not try to steal an item that belongs to " + currentApp.getItems().get(Integer.parseInt(input7) - 1).wlascicel);
                                                                        break;
                                                                    }
                                                                } else {
                                                                    System.out.println("This item does not exist ");
                                                                    break;
                                                                }
                                                            } else {
                                                                break;
                                                            }
                                                        } else {
                                                            System.out.println("There are no items in this apartment ");
                                                            break;
                                                        }
                                                        break;
                                                    }
                                                    case "0" ->{
                                                        break;
                                                    }
                                                }
                                            }else{
                                                System.out.println("You are not the owner of this apartment!");
                                            }
                                        }else{
                                            System.out.println("Apartment does not exist!");
                                        }
                                    }
                                }else{
                                    System.out.println("You have no apartments!");
                                }
                            }
                            case "2" -> {
                                if(newPerson.countMieszkania() >= 5 ){
                                    System.out.println("The limit for renting apartments has been exceeded ");
                                }else {
                                    System.out.println("Select the house, the apartments of which to show, 0 to back \n");
                                    for (int i = 0; i < blocks.size(); i++) {
                                        System.out.println((i + 1) + ". " + blocks.get(i).getInfo());
                                    }
                                    String input3 = scanner.nextLine();
                                    if (Integer.parseInt(input3) != 0) {
                                        if (Integer.parseInt(input3) <= blocks.size()) {
                                            System.out.println(blocks.get(Integer.parseInt(input3) - 1).showAllFree());
                                        } else {
                                            System.out.println("This house does not exist ");
                                            break;
                                        }
                                    } else {
                                        break;
                                    }

                                    System.out.println("Enter the apartment number for purchase, 0 to back");
                                    String input4 = scanner.nextLine();
                                    if (Integer.parseInt(input4) != 0) {
                                        if(Integer.parseInt(input4) <= mieszkanies.size() && Integer.parseInt(input4) > 0){
                                            if (!mieszkanies.get(Integer.parseInt(input4) - 1).isBuy(newPerson)) {
                                                if (mieszkanies.get(Integer.parseInt(input4) - 1).getStatus()) {
                                                    newPerson.addMieszkanie(mieszkanies.get(Integer.parseInt(input4) - 1));
                                                    System.out.println("Apartment rented successfully !");
                                                    System.out.println(mieszkanies.get(Integer.parseInt(input4) - 1));
                                                    System.out.println("Would you like to additionally rent a parking?\n1. Yes\n2. No");
                                                    String yesorno = scanner.nextLine();
                                                    if ("1".equals(yesorno)) {
                                                        newPerson.addMiesceParkingowe(miejsceParkingowes.get(Integer.parseInt(input4) - 1));
                                                        System.out.println("You have successfully purchased a parking!");
                                                    }
                                                } else {
                                                    System.out.println("The selected apartment is already occupied ");
                                                    break;
                                                }
                                            } else {
                                                System.out.println("You have already bought this apartment");
                                                break;
                                            }
                                        }else{
                                            System.out.println("The selected apartment does not exist ");
                                        }
                                    }
                                }
                                break;
                            }
                            case "3" -> {
                                if(newPerson.countMieszkania() >= 5 ){
                                    System.out.println("The limit for renting apartments has been exceeded ");
                                }else {
                                    System.out.println("Select the house, the apartments of which to show, 0 to back \n");
                                    for (int i = 0; i < blocks.size(); i++) {
                                        System.out.println((i + 1) + ". " + blocks.get(i).getInfo());
                                    }
                                    String input3 = scanner.nextLine();
                                    if (Integer.parseInt(input3) != 0) {
                                        if (Integer.parseInt(input3) <= blocks.size()) {
                                            System.out.println(blocks.get(Integer.parseInt(input3) - 1).showAllNotFree(newPerson));
                                        } else {
                                            System.out.println("This house does not exist ");
                                            break;
                                        }
                                    } else {
                                        break;
                                    }

                                    System.out.println("Enter the apartment number for purchase, 0 to back");
                                    String input4 = scanner.nextLine();
                                    if (Integer.parseInt(input4) != 0) {
                                        if(Integer.parseInt(input4) <= mieszkanies.size() && Integer.parseInt(input4) > 0){
                                            if (!mieszkanies.get(Integer.parseInt(input4) - 1).isBuy(newPerson)) {
                                                newPerson.addMieszkanie(mieszkanies.get(Integer.parseInt(input4) - 1));
                                                System.out.println("Apartment rented successfully!");
                                                System.out.println(mieszkanies.get(Integer.parseInt(input4) - 1));
                                                System.out.println("Would you like to rent an additional parking?\n1. Yes\n2. No");
                                                String yesorno = scanner.nextLine();
                                                if ("1".equals(yesorno)) {
                                                    newPerson.addMiesceParkingowe(miejsceParkingowes.get(Integer.parseInt(input4) - 1));
                                                    System.out.println("You have successfully purchased a parking!");
                                                }
                                            } else {
                                                System.out.println("You have already bought this apartment");
                                            }
                                        }else{
                                            System.out.println("The selected apartment does not exist");
                                        }
                                    }
                                }
                                break;
                            }
                            case "4" -> System.out.println(newPerson.getItems());
                            case "5" -> {
                                System.out.println(newPerson.showParking());
                                if(newPerson.countMiesca() > 0) {
                                    System.out.println("Choose a parking to interact with, 0 to back");
                                    String input8 = scanner.nextLine();
                                    MiejsceParkingowe currParking;
                                    if (Integer.parseInt(input8) != 0) {
                                        if (Integer.parseInt(input8) <= miejsceParkingowes.size() && Integer.parseInt(input8) > 0) {
                                            currParking = miejsceParkingowes.get(Integer.parseInt(input8) - 1);
                                            if (currParking.isBuy(newPerson)) {
                                                System.out.println(currParking);
                                                System.out.println("Choose an action: \n1. Show all items in the parking\n2. Add item \n3. Delete item \n0. Back");
                                                String input9 = scanner.nextLine();
                                                switch (input9) {
                                                    case "1" -> {
                                                        System.out.println(currParking.showAllItems());
                                                        break;
                                                    }
                                                    case "2" -> {
                                                        if (newPerson.countItems() > 0) {
                                                            System.out.println(newPerson.getItems());
                                                            System.out.println("Select an item to add , 0 to back");
                                                            Items newItem;
                                                            String itemid = scanner.nextLine();
                                                            if (Integer.parseInt(itemid) != 0) {
                                                                if (newPerson.getItemsArray().contains(newPerson.getItemsArray().get(Integer.parseInt(itemid) - 1))) {
                                                                    newItem = newPerson.getItemsArray().get(Integer.parseInt(itemid) - 1);
                                                                } else {
                                                                    System.out.println("There is no such item");
                                                                    break;
                                                                }
                                                                if (newItem.position == null) {
                                                                    if(currParking.freeRozmiar > newItem.rozmiar) {
                                                                        currParking.addItem(newItem);
                                                                        System.out.println("Item " + newItem.getNazwa() + " successfully added to your parking " + currParking.getId());
                                                                    }else{
                                                                        currParking.addItem(newItem);
                                                                        break;
                                                                    }
                                                                } else {
                                                                    System.out.println("The item is already in " + newItem.getPosition());
                                                                    break;
                                                                }
                                                            }
                                                            break;
                                                        } else {
                                                            System.out.println("You have no items to add");
                                                        }
                                                    }
                                                    case "3" -> {
                                                        if (currParking.countItems() > 0) {
                                                            System.out.println(currParking.showAllItems());
                                                            System.out.println("Select an item to remove, 0 to back \n");
                                                            String input7 = scanner.nextLine();
                                                            if (Integer.parseInt(input7) != 0) {
                                                                if (Integer.parseInt(input7) <= currParking.countItems() && Integer.parseInt(input7) > 0) {
                                                                    if (currParking.getItems().get(Integer.parseInt(input7) - 1).wlascicel == newPerson) {
                                                                        currParking.removeItem(currParking.getItems().get(Integer.parseInt(input7) - 1));
                                                                        System.out.println("Item deleted");
                                                                    } else {
                                                                        System.out.println("Do not try to steal an item that belongs to " + currParking.getItems().get(Integer.parseInt(input7) - 1).wlascicel) ;
                                                                        break;
                                                                    }
                                                                } else {
                                                                    System.out.println("The item does not exist ");
                                                                    break;
                                                                }
                                                            } else {
                                                                break;
                                                            }
                                                        } else {
                                                            System.out.println("There are no items on the parking");
                                                            break;
                                                        }
                                                    }
                                                    case "0" -> {
                                                        break;
                                                    }
                                                }
                                            } else {
                                                System.out.println("You are not the owner of this parking space");
                                                break;
                                            }
                                        } else {
                                            System.out.println("The selected parking space does not exist");
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }else{
                                    System.out.println("You have no parking spaces!");
                                    break;
                                }
                            }
                            case "0" -> cheker2++;
                        }
                    }while (cheker2 == 0) ;
                }

                case "2" -> {
                    ArrayList<Osiedla> testOsidlas = new ArrayList<>();
                    ArrayList<Block> testBlocks = new ArrayList<>();
                    ArrayList<Mieszkanie> testMieszkanies = new ArrayList<>();
                    ArrayList<MiejsceParkingowe> testMiejsceParkingowes = new ArrayList<>();
                    ArrayList<Person> testPersons = new ArrayList<>();
                    ArrayList<Items> testItems = new ArrayList<>();
                    //TEST DATA FOR SIMULATION
                    Osiedla testOsidla = new Osiedla(5, "Spero-Blokowa");
                    testOsidlas.add(testOsidla);

                    Block testBlock = new Block(5, 123);
                    osiedla1.addBlock(testBlock);
                    testBlocks.add(testBlock);

                    Mieszkanie testM1 = new Mieszkanie(250);
                    testMieszkanies.add(testM1);
                    testBlock.addMieszkanie(testM1);

                    MiejsceParkingowe testMP1 = new MiejsceParkingowe(250);
                    testMiejsceParkingowes.add(testMP1);
                    testBlock.addMiesce(testMP1);

                    Person testPerson1 = new Person("Bob1", "Bobby", 123, "LoL", date);
                    Person testPerson2 = new Person("Bob2", "Bobby", 123, "LoL", date);
                    Person testPerson3 = new Person("Bob3", "Bobby", 123, "LoL", date);
                    Person testPerson4 = new Person("Bob4", "Bobby", 123, "LoL", date);
                    Person testPerson5 = new Person("Bob5", "Bobby", 123, "LoL", date);
                    testPersons.add(testPerson1);
                    testPersons.add(testPerson2);
                    testPersons.add(testPerson3);
                    testPersons.add(testPerson4);
                    testPersons.add(testPerson5);

                    Items testItem1 = new Items(testPerson1, ItemsType.SAMOCHOD_MIEJSKI, "Reno Logan", 100, "Krotki opis");
                    Items testItem2 = new Items(testPerson1, ItemsType.RANDOM_ITEM, "Random box", 30, "Krotki opis");
                    Items testItem3 = new Items(testPerson1, ItemsType.RANDOM_ITEM, "Random box", 30, "Krotki opis");
                    Items testItem4 = new Items(testPerson1, ItemsType.RANDOM_ITEM, "Random box", 30, "Krotki opis");
                    testItems.add(testItem1);
                    testItems.add(testItem2);
                    testItems.add(testItem3);
                    testItems.add(testItem4);

                    //ADD LOGIC
                    testPerson1.addMieszkanie(testM1);
                    testPerson2.addMieszkanie(testM1);
                    testPerson3.addMieszkanie(testM1);
                    testPerson4.addMieszkanie(testM1);
                    testPerson5.addMieszkanie(testM1);

                    testPerson1.addMiesceParkingowe(testMP1);

                    testM1.addItem(testItem4);
                    testMP1.addItem(testItem1);
                    testMP1.addItem(testItem2);
                    testMP1.addItem(testItem3);

                    System.out.println("Simulation...");

                    MyThread thread2 = new MyThread(testPerson1, testM1, testMP1);
                    thread2.start();
                    thread2.join();

                }

                case "0" -> {
                    System.out.println("Data is being saved...");
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt"))) {
                        for (Osiedla s : osidlas) {
                            writer.write(s.printForFile());
                            writer.write("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cheker1++;
                }
            }
        }while (cheker1 == 0);
    }
}