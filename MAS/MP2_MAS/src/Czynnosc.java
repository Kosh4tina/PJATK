/*** KOMPOZYCJA ***/

public class Czynnosc {

    private String nazwa;
    private Wycieczka wycieczka;


    private Czynnosc(Wycieczka wycieczka, String nazwa) {	// PRYWATNY KONSTRUKTOR
        this.setNazwa(nazwa);
        this.wycieczka = wycieczka;
    }

    public static Czynnosc stworzCzynnosc(Wycieczka wycieczka, String nazwa) throws Exception {
        if(wycieczka == null) {
            throw new Exception("NIE ISTNIEJE");
        }

        Czynnosc czyn = new Czynnosc(wycieczka, nazwa);

        try {
            wycieczka.dodajCzynnosc(czyn);
        } catch (Exception e) {
            System.out.println("NIE MOZNA DODAC");
        }

        return czyn;
    }


    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}