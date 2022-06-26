import functions.TooManyThingsEX;

import java.time.LocalDate;
import java.util.ArrayList;

public class Mieszkanie extends Pomieszcznenie{
    Person najemca = null;
    boolean isFree = true;
    String dataOd = null;
    String dataDo = null;

    private final ArrayList<Person> persons = new ArrayList<>();
    private final ArrayList<Items> items = new ArrayList<>();

    public Mieszkanie(double rozmiar){
       super(rozmiar);
    }

    public Mieszkanie(double dlugosc, double sherokosc, double wysokosc){
        super(dlugosc, sherokosc, wysokosc);
    }

    public String getNazwa(){
        return "Apartments number " + getId();
    }

    public boolean isBuy(Person person){
        return persons.contains(person);
    }

    public boolean isNajemca(Person person){
        return this.najemca == person;
    }

    public void changeStatus(){
        this.isFree = !isFree;
    }

    public boolean getStatus(){
        return isFree;
    }

    public double getRozmiar(){
        return  rozmiarPowierchni;
    }

    public void setDataOd(String dataOd){
        this.dataOd = dataOd;
    }

    public void setDataDo(String dataDo){
        this.dataDo = dataDo;
    }

    public void addSomeDays(int equal) {
        this.dataDo = LocalDate.parse(dataDo).plusDays(equal).toString();
    }

    public String getDataOd() {
        return dataOd;
    }

    public String getDataDo() {
        return dataDo;
    }

    public void addPerson(Person nowyPerson) {
        if (!persons.contains(nowyPerson)) {
            if(this.getStatus()){
                this.najemca = nowyPerson;
                this.changeStatus();
            }
            persons.add(nowyPerson);
            nowyPerson.addMieszkanie(this);
        }
    }

    public void removePerson(Person person){
        if(persons.contains(person)){
           if(this.isNajemca(person)){
               for(int i =0; i<persons.size();i++){
                   Person newPerson = persons.get(i);
                   persons.remove(newPerson);
                   newPerson.removeMieszkanie(this);
               }
               this.najemca = null;
               this.changeStatus();
           }else{
               persons.remove(person);
               person.removeMieszkanie(this);
           }
        }
    }

    public String showAllPersons(){
        StringBuilder info = new StringBuilder("");
        if(this.najemca != null){
            info.append("\nTenant: ").append(najemca);
            for(int i =1; i<persons.size(); i++){
                info.append("\nNeighbor №").append(i).append(": ").append(persons.get(i));
            }
        }else{
            info.append("\nApartments is free for buying!");
        }

        return info.toString();
    }

    public void addItem(Items noweItem) {
        try {
            if (!items.contains(noweItem)) {
                if(freeRozmiar >= noweItem.rozmiar){
                    items.add(noweItem);

                    changeRozmiarMin(noweItem.rozmiar);
                    noweItem.setPosition(this.getNazwa());

                    noweItem.addMieszkanie(this);
                }else{
                    throw new TooManyThingsEX();
                }

            }
        } catch (TooManyThingsEX e) {
            e.printStackTrace();
        }
    }

    public void removeItem(Items item){
        if(items.contains(item)){
            item.setPosition(null);
            items.remove(item);
            changeRozmiarAdd(item.rozmiar);

            item.removeMieszkanie(this);
        }
    }

    public int countItems(){
        return items.size();
    }

    public ArrayList<Items> getItems(){
        return items;
    }

    public String showAllItems(){
        String info = "";
        if(countItems() > 0) {
            info+= "Contains items: \n";
            for (int i = 0; i < items.size(); i++) {
                info += (i + 1) + ". " + items.get(i) + "\n";
            }
        }else {
            info += "This apartments doesn't have any items";
        }
        return info;
    }

    public static void sort(ArrayList<Items> list) {
        list.sort((o1, o2) -> {
            if(Double.compare(o2.getRozmiar(), o1.getRozmiar()) == 0){
                return o1.getNazwa().compareTo(o2.getNazwa());
            }else{
                return Double.compare(o2.getRozmiar(), o1.getRozmiar());
            }
        });
    }

    public String printForFile(){
        String info = "";
        info += this.toString();
        if (countItems() > 0){
            sort(items);
            info += "\nContains items: ";
            for (int i = 0; i<items.size(); i++){
                info += "\n" + (i+1) + ". " + items.get(i).printForFile();
            }
        }
        info += this.showAllPersons();

            return info;
    }

    public String toString(){
        String info = "";

        info += "Apartments number " + getId() + ", size: " + getRozmiar() + ", free size: " + getFreeRozmiar();

        if(!isFree){
            info+= "\n" + "The apartment was rented on: " + getDataOd() + ", the next payment must be made on: " + getDataDo();
        }

        return info;
    }
}


