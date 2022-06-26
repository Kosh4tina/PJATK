import java.util.ArrayList;

/*** ASOCJACJA BINARNA ***/

public class Guardian extends Osoba {

    private ArrayList<Rhinoceros> rhinoceros = new ArrayList<>();


    public Guardian(String imie, String nazwisko) {
        super(imie, nazwisko);
    }



    public void addRhinoceros(Rhinoceros nowyRhino)
    {
        if(!rhinoceros.contains(nowyRhino)) {   // CZY NIE MA JUZ INFO. O TYM NOSOROZCE?
            rhinoceros.add(nowyRhino);

            nowyRhino.addGuardian(this);		// INFORMACJA ZWROTNA
        }
    }


    public String toString()
    {
        String zwrot = "";
        int licznik = 0;

        zwrot += "NAZWISKO OPIEKUNA: " + getNazwisko();

        zwrot += "\nNOSOROZEC POD OPIEKA: ";

        for(Rhinoceros o: rhinoceros) {
            zwrot += "\n" + ++licznik + ". " + o.getNazwa();
        }

        zwrot += "\n----------------------";

        return zwrot;
    }
}
