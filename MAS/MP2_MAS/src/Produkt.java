import java.util.ArrayList;

/*** ASOCJACJA KWALIFIKOWANA ***/

public class Produkt {

    private String nazwa;
    private int nrKolejny;

    private ArrayList<Meal> meal = new ArrayList<>();	// przechowywanie informacji zwrotnej

    public Produkt(int nrKolejny, String nazwa) {
        this.setNrKolejny(nrKolejny);
        this.nazwa = nazwa;
    }

    public void addMeal(Meal nowyMeal)
    {
        if(!meal.contains(nowyMeal)) {	  // Sprawdz czy nie mamy juz takiej informacji
            meal.add(nowyMeal);

            nowyMeal.addProduktKwalif(this);		// Dodaj informacje zwrotna
        }
    }

    public String toString() {
        System.out.println("----------------------");
        String zwrot = "PRODUKT: " + nazwa + "\n";

        zwrot += "WCHODZI W SKЈAD POSIЈKU: ";
        for(Meal p : meal) {
            zwrot += p.getNazwa()+ "\n";
        }

        return zwrot;
    }


    public int getNrKolejny() {
        return nrKolejny;
    }
    public void setNrKolejny(int nrKolejny) {
        this.nrKolejny = nrKolejny;
    }
}
