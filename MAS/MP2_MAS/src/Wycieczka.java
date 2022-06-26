import java.time.LocalDate;
import java.util.HashSet;
import java.util.Vector;

/*** KOMPOZYCJA ***/

public class Wycieczka {

    private String rodzaj;
    private LocalDate dataRozpoczecia;
    private LocalDate dataZakonczenia;

    private Vector<Czynnosc> czynnosci = new Vector<>();

    private static HashSet<Czynnosc> wszystkieCzynnosci = new HashSet<>(); 	// Kontener pamiatajacy referencje do wszystkich czesci (polaczonych ze wszystkimi obiektami-calociami)
                                                                            // Dzieki temu mozna sprawdzic czy jakas czesc jest juz polaczona z ktorymkolwiek obiektem-calosci

    public Wycieczka(String rodzaj, LocalDate dataRozpoczecia, LocalDate dataZakonczenia)
    {
        this.rodzaj = rodzaj;
        this.dataRozpoczecia = dataRozpoczecia;
        this.dataZakonczenia = dataZakonczenia;
    }


    public void dodajCzynnosc(Czynnosc czyn)
    {
        if(!czynnosci.contains(czyn))
        {
            if(wszystkieCzynnosci.contains(czyn)) {
                System.out.println("JEST JUZ POWIAZANA");
            }

            czynnosci.add(czyn);

            wszystkieCzynnosci.add(czyn);
        }
    }


    public String toString() {
        String info = "WYCIECZKA: " + rodzaj + " o godzine: " + dataRozpoczecia + "\n";

        info += "sklada sie z: \n";
        int licznik = 0;
        for(Czynnosc cz : czynnosci) {
            licznik++;
            info += licznik + ". " + cz.getNazwa() + "\n";
        }

        return info;
    }
}
