import java.time.LocalDate;

/***KLASA ASOCJACYJNA***/

public class RhinocerosMeal {
    private LocalDate dataPrzyjecia;
    private int ilosc;

    private Rhinoceros rhinoceros;
    private Meal meal;

    public RhinocerosMeal(LocalDate dataPrzyjecia, int ilosc, Rhinoceros rhinoceros, Meal meal)
    {
        this.dataPrzyjecia = dataPrzyjecia;
        this. ilosc = ilosc;

        this.rhinoceros = rhinoceros;
        this.meal = meal;

        rhinoceros.addRhinoMeal(this);
        meal.addRhinoMeal(this);
    }

    public void setMeal(Meal meal) {
        if(this.meal != null) {		// Czy istnieje powiazanie meal - RhinoMeal
            this.meal = null;

            try {
                meal.removeM();		// Jezeli powiazanie meal - RhinoMeal istnieje usun
            } catch (Exception e) {
                System.out.println("NIE MOZNA USUNAC " + e);
            }
        }
        this.meal = meal;	// Ustaw nowe
        meal.addRhinoMeal(this);
    }



    public void setRhinoceros(Rhinoceros rhinoceros) {
        if(this.rhinoceros == null) {
            this.rhinoceros = rhinoceros;
            rhinoceros.addRhinoMeal(this);
        }
        else {
            this.rhinoceros = null;

            try {
                rhinoceros.removeR();
            } catch (Exception e) {
                System.out.println("NIE MOZNA USUNAC " + e);
            }

            this.rhinoceros = rhinoceros;
            rhinoceros.addRhinoMeal(this);
        }
    }

    public String toString()
    {
        String zwrot = "";

        zwrot += "NOSOROZEC: " + rhinoceros.getNazwa() + "\n";

        zwrot += "POSIЈEK: " + meal.getNazwa();

        zwrot += "\n----------------------";

        return zwrot;
    }

    public LocalDate getDataPrzyjecia() {
        return dataPrzyjecia;
    }
}
