import functions.ItemsType;
import java.util.ArrayList;

public class Items{
    Person wlascicel;
    String position = null;
    ItemsType typ;
    String nazwa;
    double rozmiar;
    String opis;
    ArrayList<CechiCharakterystyczne> ch = new ArrayList<>();

    private final ArrayList<MiejsceParkingowe> miesces = new ArrayList<>();
    private final ArrayList<Mieszkanie> mieszkanies = new ArrayList<>();

    public Items(Person wlascicel, ItemsType typ, String nazwa, double rozmiar, String opis){
        this.wlascicel = wlascicel;
        addPerson(wlascicel);
        setTyp(typ);
        setNazwa(nazwa);
        setRozmiar(rozmiar);
        setOpis(opis);

    }

    public Items(Person wlascicel, ItemsType typ, String nazwa, double sherokosc,double dlugosc,double wysokosc, String opis){
        this.wlascicel = wlascicel;
        addPerson(wlascicel);
        setTyp(typ);
        setNazwa(nazwa);
        setRozmiar(sherokosc, dlugosc, wysokosc);
        setOpis(opis);

    }

    public void addCH(String nazwaCH, String wartoscCH){
        CechiCharakterystyczne cechiCharakterystyczne = new CechiCharakterystyczne(nazwaCH, wartoscCH);
        if(!ch.contains(cechiCharakterystyczne)){
            ch.add(cechiCharakterystyczne);
        }
    }

    public void addPerson(Person person){
        person.addItem(this);
    }

    public void setTyp(ItemsType typ) {
        this.typ = typ;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setRozmiar(double rozmiar) {
        this.rozmiar = rozmiar;
    }

    public void setRozmiar(double dlugosc, double sherokosc, double wysokosc){
        double rozmiar = 2* ((dlugosc*sherokosc) + (sherokosc*wysokosc) + (wysokosc*dlugosc));
        this.rozmiar = rozmiar;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getWlascicel() {
        if(wlascicel != null){
            return wlascicel.getImie();
        }else{
            return "The item was seized as debt repayment";
        }

    }

    public ItemsType getTyp() {
        return typ;
    }

    public String getPosition(){
        return position;
    }

    public String getNazwa() {
        return nazwa;
    }

    public double getRozmiar() {
        return rozmiar;
    }

    public String getOpis() {
        return opis;
    }

    public void addMiesceParkingowe(MiejsceParkingowe mejsce) {
        if (!miesces.contains(mejsce)) {
            miesces.add(mejsce);

            mejsce.addItem(this);
        }
    }

    public void removeMiesce(MiejsceParkingowe miejsce){
        if(miesces.contains(miejsce)){
            miesces.remove(miejsce);

            miejsce.removeItem(this);
        }
    }

    public void addMieszkanie(Mieszkanie mieszkanie) {
        if (!mieszkanies.contains(mieszkanie)) {
            mieszkanies.add(mieszkanie);

            mieszkanie.addItem(this);
        }
    }

    public void removeMieszkanie(Mieszkanie mieszkanie){
        if(mieszkanies.contains(mieszkanie)){
            mieszkanies.remove(mieszkanie);

            mieszkanie.removeItem(this);
        }
    }

    public String showCechy(){
        StringBuilder info = new StringBuilder();
        if(ch.size() > 0){
            for (CechiCharakterystyczne cechiCharakterystyczne : ch) {
                info.append("\n - ").append(cechiCharakterystyczne.getNazwaCechi()).append(" : ").append(cechiCharakterystyczne.getWartoscCechi());
            }
        }

        return info.toString();
    }

    public String printForFile(){
        return  "Item: " + getNazwa() + ", size: " + getRozmiar() + "\nTyp: " + getTyp() + showCechy() + "\nOwner: " + getWlascicel();
    }

    public String toString(){
        String info = "Item: " + getNazwa() + ", size: " + getRozmiar() + "\nTyp: " + getTyp();
        info += showCechy();
        info +=  "\nOwner: " + getWlascicel();
        if(position != null){
            info += "\nPlace of overlap of the object : " + position;
        }
        return info;
    }
}
