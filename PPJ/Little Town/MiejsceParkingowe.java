import functions.TooManyThingsEX;

import java.util.ArrayList;

public class MiejsceParkingowe extends Pomieszcznenie {

    private final ArrayList<Person> persons = new ArrayList<>();
    private final ArrayList<Items> items = new ArrayList<>();

    public MiejsceParkingowe(double rozmiar){
        super(rozmiar);
    }

    public MiejsceParkingowe(double dlugosc, double sherokosc, double wysokosc){
        super(dlugosc, sherokosc, wysokosc);
    }

    public boolean isBuy(Person person){
        return persons.contains(person);
    }

    public int getId() {
        return id-10;
    }

    public String getNazwa(){
        return "Parking space number  " + getId();
    }

    public double getRozmiarPowierchni() {
        return rozmiarPowierchni;
    }

    public double getFreeRozmiar(){
        return freeRozmiar;
    }

    public void addPerson(Person nowyPerson){
        if(!persons.contains(nowyPerson)) {
            persons.add(nowyPerson);

            nowyPerson.addMiesceParkingowe(this);
        }
    }

    public void removePerson(Person person){
        if(persons.contains(person)){
            persons.remove(person);

            person.removeMiesce(this);
        }
    }

    public void addItem(Items noweItem) {
        try {
            if (!items.contains(noweItem)) {
                if (freeRozmiar >= noweItem.rozmiar){
                    items.add(noweItem);

                    changeRozmiarMin(noweItem.rozmiar);
                    noweItem.setPosition(this.getNazwa());

                    noweItem.addMiesceParkingowe(this);
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

            item.removeMiesce(this);
        }
    }

    public int countItems(){
        return items.size();
    }

    public ArrayList<Items> getItems(){
        return items;
    }

    public String showAllItems(){
        StringBuilder info = new StringBuilder();
        if(countItems() > 0){
            info.append("\nContains items: \n");
            for(int i =0; i<items.size(); i++){
                sort(items);
                info.append(i + 1).append(". ").append(items.get(i)).append("\n");
            }
        }else{
            info.append("\nThis parking doesn't have any items");
        }

        return info.toString();
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
        String info ="Parking space number  " + getId() + ", size: " + getRozmiarPowierchni() + ", free size: " + getFreeRozmiar();
        info += showAllItems();
        return info;
    }

    public String toString(){
        String info = "";
        info += "Parking space number " + getId() + ", size: " + getRozmiarPowierchni() + ", free size: " + getFreeRozmiar();
        return info;
    }
}
