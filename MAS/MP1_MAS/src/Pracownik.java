import java.util.Date;

public abstract class Pracownik extends Extension {

    private static final long serialVersionUID = 1L;

    protected String imie;// atrybut prosty, wymagany, pojedynczy, obiektu
    protected String nazwisko;// atrybut prosty, wymagany, pojedynczy, obiektu
    protected Date dataUrodzenia;// atrybut zlozony, opcjonalny, pojedynczy, obiektu
    protected String adres;// atrybut prosty, opcjonalny, pojedynczy, obiektu
    protected String nrTelefonu;// atrybut prosty, wymagany, pojedynczy, obiektu
    protected String plec;// atrybut prosty, wymagany, pojedynczy, obiektu
    protected int pensia;// atrybut,wymagany, potrzebny do wyliczen, obiektu
    protected static double sredniaPensia;// atrybut prosty, pojedynczy, pochodny, klasowy
    private static int iloscPracownikow = 0; // atrybut prosty, pojedynczy, klasowy - potrzebny do liczenia sredniej

    Pracownik(String imieOsoby, String nazwiskoOsoby, Date dataUrodzeniaOsoby, String adresOsoby, String nrTelefonuOsoby, String plecOsoby, int pensiaOsoby)
    {
        super();//uruchomienie konstruktora w klasie Extension(tworzenie listy ekstensji)

        if(imieOsoby == null){throw new NullPointerException("The name field cannot be empty.");}
        this.imie = imieOsoby;

        if(nazwiskoOsoby == null){throw new NullPointerException("The surname field cannot be empty.");}
        this.nazwisko = nazwiskoOsoby;

        if(nrTelefonuOsoby == null){throw new NullPointerException("The phone number field cannot be empty.");}
        this.nrTelefonu = nrTelefonuOsoby;

        if(plecOsoby == null){throw new NullPointerException("The gender field cannot be empty.");}
        this.plec = plecOsoby;

        this.dataUrodzenia = dataUrodzeniaOsoby;

        this.pensia = pensiaOsoby;

        this.adres = adresOsoby;

        iloscPracownikow++;
        sredniaPensia += (this.pensia - sredniaPensia) / iloscPracownikow; //wyliczzanie średniej ilości pensii na osobe
    }

    //prszeslanienie metod
    public String getName(){
        return this.imie;
    }

    public String getSurname(){
        return this.nazwisko;
    }

    public String getPhoneNumber(){
        return this.nrTelefonu;
    }

    public String getSex(){
        return this.plec;
    }

    public String getAddress(){
        return this.adres;
    }

    public Date getBirthdayDate(){
        return this.dataUrodzenia;
    }

    public int getSalary(){
        return this.pensia;
    }

    //meteda klasowa operuje na zmiennych klasowych
    public static double getAverageSalary(){
        return sredniaPensia;
    }

    public void setName(String imie){
        if(imie == null){throw new NullPointerException("The name field cannot be empty.");}
        this.imie = imie;
    }

    public void setSurname(String nazwisko){
        if(nazwisko == null){throw new NullPointerException("The surname field cannot be empty.");}
        this.nazwisko = nazwisko;
    }

    public void setBirthdayDate(Date dataUrodzenia){
        this.dataUrodzenia = dataUrodzenia;
    }

    public void setPhoneNumber(String nrTelefonu){
        if(nrTelefonu == null){throw new NullPointerException("The phone number field cannot be empty.");}
        this.nrTelefonu = nrTelefonu;
    }

    public void setSex(String plec){
        if(plec == null){throw new NullPointerException("The gender field cannot be empty.");}
        this.plec = plec;
    }

    public void setAddress(String address)
    {
        this.adres = address;
    }

    public void setSalary(int salary){
        this.pensia = salary;
    }

    public String toString(){
        String join = "";
        join += "Pracownik" + "\nImie: " +getName() +
                "\nNazwisko: " + getSurname() +
                "\nNr Telefonu: ";
        join += nrTelefonu;

        join += "\nData Urodzenia: ";
        if(dataUrodzenia != null)
            join += dataUrodzenia;
        else
            join += "brak danych";

        join += "\nAdres: ";
        if(adres != null)
            join += adres;
        else
            join += "brak danych";

        join += "\nPłeć: " + getSex();

        join += "\nPensia: " + getSalary();

        return join;
    }

}

