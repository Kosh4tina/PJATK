import java.util.ArrayList;

public class Osiedla{
    int id;
    String nazwa;

    private final ArrayList<Block> blocks = new ArrayList<>();
    private final ArrayList<Person> personArrayList = new ArrayList<>();

    public Osiedla(int id, String nazwa){
        this.id = id;
        this.nazwa = nazwa;
    }

    public void addBlock(Block block){
        if(!blocks.contains(block)){
            blocks.add(block);
        }
    }

    public void addPerson(Person person){
        if(!personArrayList.contains(person)){
            personArrayList.add(person);
        }
    }

    public String getNazwaOsiedla() {
        return nazwa;
    }

    public String getInfo(){
        return "Residential " + getNazwaOsiedla();
    }

    public String printForFile(){
        String info = "";
        info += "Residential " + getNazwaOsiedla() + "\nContains blocks: ";
        for (Block block : blocks) {
            info += ("\n=============================================================");
            info += "\n" + block.printForFile();
        }
        info += "\nAll residents and their property: ";
        for(int i = 0; i<personArrayList.size(); i++){
            info += ("\n=============================================================");
            info += "\n" + (i+1) + ". " + personArrayList.get(i).toString() + " " + personArrayList.get(i).showNaruszenia() + " " + personArrayList.get(i).getItems();
        }
        return info;
    }

    public String toString(){
        String info = "Residential " + getNazwaOsiedla() + "\nContains blocks: ";

        for(int i = 0; i<blocks.size(); i++){
            info += "\n";
            info += (i+1) + " " + blocks.get(i);
        }

        return info;
    }
}
