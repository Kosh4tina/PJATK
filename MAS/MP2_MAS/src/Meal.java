import java.util.ArrayList;
import java.util.TreeMap;


public class Meal {

    private String nazwa;

    private ArrayList<RhinocerosMeal> rhinocerosMeals = new ArrayList<>();	// ASOCJACJA Z ATRYBUT

    private TreeMap<Integer, Produkt> produktKwalif = new TreeMap<>();		// ASOCJACJA KWALIFIKOWANA - kontener mapujacy, gdzie kluczem
                                                                            // jest NUMER KOLEJNY produktu w posiіku,
                                                                            // a wartoscia referencja do obiektu klasy Produkt
    public Meal(String nazwa) {
        this.nazwa = nazwa;
    }

    /*** ASOCJACJA Z ATRYBUT ***/

    public void addRhinoMeal(RhinocerosMeal rhinocerosMeal)
    {
        if(!this.rhinocerosMeals.contains(rhinocerosMeal)) {   // CZY NIE MA JUZ INFO, O TYM NOSOROZCE?
            this.rhinocerosMeals.add(rhinocerosMeal);
        }
    }
    public void RemoveRhinoMeal(RhinocerosMeal rhino) {
        rhinocerosMeals.remove(rhino);
    }

    @SuppressWarnings("unchecked")
    public void removeM()							// METODA USUWAJACA POWIAZANIE nosorozec nosorozec-posilek
    {											    // nie mozna usuwac elementуw, po ktуrych sie iteruje
        for(RhinocerosMeal rhino : (ArrayList<RhinocerosMeal>) rhinocerosMeals.clone())		// kopia listy i iterowanie po niej
        {
            this.RemoveRhinoMeal(rhino);
        }
        rhinocerosMeals.clear();
    }

    /*** KONIEC ASOCJACJI Z ATRYBUTEM ***/


    /*** ASOCJACJA KWALIFIKOWANA ***/

    public void addProduktKwalif(Produkt nowyProdukt)			// Tworzy poіlaczenie w ramach asocjacji kwalif.
    {
        if(!produktKwalif.containsKey(nowyProdukt.getNrKolejny()))			// Sprawdz czy nie mamy juz informacji o Produkcie o tym numerze
        {
            produktKwalif.put(nowyProdukt.getNrKolejny(), nowyProdukt);		// Dodajemy zapis do mapy

            nowyProdukt.addMeal(this);			// Dodaj informacjк zwrotna
        }
    }

    public Produkt findProduktKwalif(int nrKolejny) throws Exception		// Odszukuje obiekt docelowy (produkt) na podstawie kwalifikatora (nrKolejny)
    {
        if(!produktKwalif.containsKey(nrKolejny)) {		// Sprawdz czy nie mamy juz informacji o tym powiazaniu
            throw new Exception("Nie odnaleziono produktu o numerze: " + nrKolejny);
        }

        return produktKwalif.get(nrKolejny);		// Zwroc wartosc na podstawie odpowiadajacej jej wartosci
    }

    /*** KONIEC ASOCJACJI KWALIFIKOWANEJ ***/

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
