import functions.ProblematicTenantException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Person{
    String imie;
    String nazwisko;
    int Pesel;
    String adress;
    Date dataUrodzenia;
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private final ArrayList<Mieszkanie> mieszkanies = new ArrayList<>();
    private final ArrayList<MiejsceParkingowe> miejsceParkingowes = new ArrayList<>();
    private final ArrayList<Items> items = new ArrayList<>();
    private final ArrayList<FileMessage> fileMessages = new ArrayList<>();

    public Person(String imie, String nazwisko, int Pesel, String adress, Date dataUrodzenia){
        setImie(imie);
        setNazwisko(nazwisko);
        setPesel(Pesel);
        setAdress(adress);
        setDataUrodzenia(dataUrodzenia);
    }

    public void setPesel(int pesel) {
        Pesel = pesel;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setDataUrodzenia(Date dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public int getPesel() {
        return Pesel;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getAdress() {
        return adress;
    }

    public Date getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void addFileM(Mieszkanie mieszkanie, String body){
        double id = 100000 + Math.random()*500000;

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String data = dateFormat.format(date);

        FileMessage fileMessage = new FileMessage(Math.round(id), data, this, mieszkanie, body);

        if(!fileMessages.contains(fileMessage)){
            fileMessages.add(fileMessage);
        }
    }

    public void removeFileM(FileMessage fileMessage){
        fileMessages.remove(fileMessage);
    }

    public int fileCount(){
        return fileMessages.size();
    }

    public void addItem(Items item){
        if(!items.contains(item)){
            items.add(item);
        }
    }

    public void removeItem(Items item){
        if(items.contains(item)){
            item.wlascicel = null;
            items.remove(item);
        }
    }

    public int countItems(){
        return items.size();
    }

    public ArrayList<Items> getItemsArray(){
        return items;
    }

    public String getItems(){
        StringBuilder info = new StringBuilder();
        if(items.size() == 0){
            return "You have no items";
        }else{
            info.append("\nContains items: ");
            for(int i = 0; i<items.size(); i++){
                info.append("\n").append(i + 1).append(". \n").append(items.get(i));
            }
        }
        return info.toString();
    }

    public int countMieszkania(){
        return mieszkanies.size();
    }

    public int countMiesca(){
        return miejsceParkingowes.size();
    }

    public void addMieszkanie(Mieszkanie noweMieszkanie){
        if(!mieszkanies.contains(noweMieszkanie) && mieszkanies.size() < 5) {
            try {
                if(fileCount() > 2){
                    String error = "Osoba " + this.getImie() + " posiadala juz najem pomieszczen i nie placila za to: ";
                    for(int i = 0; i<fileMessages.size(); i++){
                        error += "\n" + (i+1) + ". " + fileMessages.get(i).getMieszkanie();
                    }
                    throw new ProblematicTenantException(error);
                }
            } catch (ProblematicTenantException e) {
                e.printStackTrace();
            }
            mieszkanies.add(noweMieszkanie);

            Date data = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date = formatter.format(data);
            String dataDo = LocalDate.parse(date).plusDays(30).toString();

            noweMieszkanie.setDataOd(date);
            noweMieszkanie.setDataDo(dataDo);
            noweMieszkanie.addPerson(this);
        }
    }

    public void removeMieszkanie(Mieszkanie mieszkanie){
        if(mieszkanies.contains(mieszkanie)){

            if(mieszkanie.countItems() > 0){
                for(int i = 0; i<mieszkanie.countItems(); i++){
                    if(mieszkanie.getItems().get(i).wlascicel == this){
                        mieszkanie.getItems().get(i).setPosition(null);
                        mieszkanie.removeItem(mieszkanie.getItems().get(i));
                    }
                }
            }

            mieszkanie.setDataDo(null);
            mieszkanie.setDataOd(null);

            mieszkanies.remove(mieszkanie);

            mieszkanie.removePerson(this);

        }
    }

    public void addMiesceParkingowe(MiejsceParkingowe noweMiesce){
        if(!miejsceParkingowes.contains(noweMiesce) && miejsceParkingowes.size() <= 5) {
            miejsceParkingowes.add(noweMiesce);

            noweMiesce.addPerson(this);
        }
    }

    public void removeMiesce(MiejsceParkingowe miesce){
        if(miejsceParkingowes.contains(miesce)){

            if(miesce.countItems() > 0){
                for(int i = 0; i<miesce.countItems(); i++){
                    if(miesce.getItems().get(i).wlascicel == this){
                        miesce.getItems().get(i).setPosition(null);
                        miesce.removeItem(miesce.getItems().get(i));
                    }
                }
            }

            miejsceParkingowes.remove(miesce);

            miesce.removePerson(this);
        }
    }

    public String showMieszkania(){
        String info = "";
        if(countMieszkania() > 0){
            info += "Contains apartments: ";
            for(int i = 0; i < countMieszkania();i++){
                info += "\n" + (i+1) + ". " + mieszkanies.get(i);
            }
        }else{
            System.out.println("You don't have any apartments yet");
        }
        return info;
    }

    public String showParking(){
        String info = "";
        if(miejsceParkingowes.size() > 0){
            info += "Contains Parking places: \n";
            for(int i =0; i< miejsceParkingowes.size(); i++){
                info+= (i+1) + " " + miejsceParkingowes.get(i) + "\n";
            }
        }else{
            info += "You don't have any parking spaces yet";
        }

        return info;
    }

    public String showNaruszenia(){
        StringBuilder info = new StringBuilder();
        if(fileCount() > 0){
            info.append("\nViolations: ");
            for(int i = 0; i<fileMessages.size(); i++){
                info.append("\n").append(i + 1).append(". ").append(fileMessages.get(i).toString());
            }
        }
        return info.toString();
    }

    public String toString(){
        String info = imie + " " + nazwisko + "\n";

        String simpleDateUr = simpleDateFormat.format(dataUrodzenia);

        info += "Date of birth: " + simpleDateUr + "\n";

        info += "Address: " + adress;

        return info;
    }
}
