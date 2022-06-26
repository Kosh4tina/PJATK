import java.util.ArrayList;
import java.util.Comparator;


public class Block{
    int id;
    int numerDomu;

    private static final ArrayList<Mieszkanie> mieszkanie = new ArrayList<>();
    private final ArrayList<MiejsceParkingowe> miejsceParkingowes = new ArrayList<>();

    public Block(int id, int numerDomu){
        this.id = id;
        this.numerDomu = numerDomu;
    }

    public int getId() {
        return id;
    }

    public int getNumerDomu() {
        return numerDomu;
    }

    public void addMieszkanie(Mieszkanie noweMieszkanie) {
        if(!mieszkanie.contains(noweMieszkanie)) {
            mieszkanie.add(noweMieszkanie);
        }
    }

    public void addMiesce(MiejsceParkingowe miejse){
        if(!miejsceParkingowes.contains(miejse)){
            miejsceParkingowes.add(miejse);
        }
    }

    public String showAllFree(){
        StringBuilder info = new StringBuilder("Free apartments: ");
        for (Mieszkanie value : mieszkanie) {
            if (value.isFree) {
                info.append("\n");
                info.append(value);
            }
        }
        return info.toString();
    }

    public String showAllNotFree(Person person){
        StringBuilder info = new StringBuilder("Busy apparments: ");
        for (Mieszkanie value : mieszkanie) {
            if (!value.isFree && !value.isBuy(person)) {
                info.append("\n");
                info.append(value);
            }
        }
        return info.toString();
    }

    public String getInfo(){
        return "Block number: " + getNumerDomu();
    }

    public static void sort(ArrayList<Mieszkanie> list){
        list.sort(Comparator.comparingDouble(Mieszkanie::getRozmiar));
    }

    public String printForFile(){
        StringBuilder info = new StringBuilder("Block number" + this.getNumerDomu());
        info.append("\nContains Apartments: ");
        sort(mieszkanie);
        for (Mieszkanie value : mieszkanie) {
            info.append("\n").append(value.printForFile());
            info.append("\n=============================================================");
        }

        info.append("\nContains Parking spaces: ");
        for (MiejsceParkingowe miejsceParkingowe : miejsceParkingowes) {
            info.append("\n").append(miejsceParkingowe.printForFile());
            info.append("\n=============================================================");
        }
        return info.toString();
    }

    public String toString(){
        StringBuilder info = new StringBuilder("Contains apartments: ");

        for(int i = 0; i<mieszkanie.size(); i++){
            info.append("\n");
            info.append(i).append(1).append(" ").append(mieszkanie.get(i));
        }

        return info.toString();
    }
}
