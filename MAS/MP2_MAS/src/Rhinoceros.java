import java.util.ArrayList;

public class Rhinoceros
{
    private String nazwa;
    private String pseudonim;
    private String plec;
    private int waga;

    private ArrayList<Guardian> guardian = new ArrayList<>();		    // ASOCJACJA BINARNA
    private ArrayList<RhinocerosMeal> RhinoMeal = new ArrayList<>();	// ASOCJACJA Z ATRYBUT

    public Rhinoceros(String nazwa, String pseudonim, String plec, int waga)
    {
        this.setNazwa(nazwa);
        this.pseudonim = pseudonim;
        this.plec = plec;
        this.waga = waga;
    }

    /*** ASOCJACJA BINARNA ***/

    public void addGuardian(Guardian nowyOpiekun)
    {
        if(!guardian.contains(nowyOpiekun)) {   // CZY NIE MA JUZ INFO. O TYM OPIEKUNIE?
            guardian.add(nowyOpiekun);

            nowyOpiekun.addRhinoceros(this);	// INFORMACJA ZWROTNA
        }
    }

    /*** KONIEC ASOCJACJI BINARNARJ ***/

    /*** ASOCJACJA Z ATRYBUT ***/

    public void addRhinoMeal(RhinocerosMeal RhinoMeal)
    {
        if(!this.RhinoMeal.contains(RhinoMeal)) {   // CZY NIE MA JUZ INFO. O TYM NOSOROZCE?
            this.RhinoMeal.add(RhinoMeal);
        }
    }
    public void removeRhinoMeal(RhinocerosMeal rhino) {
        RhinoMeal.remove(rhino);
    }

    @SuppressWarnings("unchecked")
    public void removeR()							// METODA USUWAJACA
    {											    // nie mozna usuwac elementow, po ktуrych sie iteruje
        for(RhinocerosMeal rhino : (ArrayList<RhinocerosMeal>) RhinoMeal.clone())		// kopia listy
        {
            this.removeRhinoMeal(rhino);
        }
        RhinoMeal.clear();
    }

    /*** KONIEC ASOCJACJI Z ATRYBUT ***/


    public String toString()
    {
        String zwrot = "";
        int licznik = 0;

        zwrot += "NAZWA NOSOROZCA: " + getNazwa();

        zwrot += "\nAKTUALNY OPIEKUN: ";
        for(Guardian o: guardian) {
            zwrot += "\n" + ++licznik + ". " + o.getNazwisko();
        }

        zwrot += "\nKIEDY JADL: ";
        for(RhinocerosMeal dp: RhinoMeal) {
            zwrot += "\n" + ++licznik + ". " + dp.getDataPrzyjecia();
        }

        zwrot += "\n----------------------";

        return zwrot;
    }

    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}