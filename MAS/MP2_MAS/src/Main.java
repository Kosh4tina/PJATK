import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception {

        LocalDate date = LocalDate.now();

        Rhinoceros jawajski = new Rhinoceros("Nosorozec jawajski", "Jawi", "samiec", 500);
        Rhinoceros bialy = new Rhinoceros("Bialy nosorozec", null, "samica", 700);

        Guardian rodriges = new Guardian("Rodger", "Rodriges");
        Guardian osolejwa = new Guardian("Marta", "Osolejwa");

        Meal duzeDanie = new Meal("Duze Danie");
        Meal wegetarianskieDanie = new Meal("Wegetariaсskie Danie");

        RhinocerosMeal rhinoMeal1 = new RhinocerosMeal(date, 200, jawajski, duzeDanie);
        RhinocerosMeal rhinoMeal2 = new RhinocerosMeal(date, 50, bialy, wegetarianskieDanie);

        Wycieczka wyc = new Wycieczka("KROTKA WYCIECZKA", date, date);

        Produkt mieso = new Produkt(1, "Mieso");
        Produkt zielenina = new Produkt(2, "Zielenina");


        // INFORMACJE O POWIAZANIACH

        jawajski.addGuardian(rodriges);
        jawajski.addGuardian(osolejwa);
        bialy.addGuardian(osolejwa);

        rhinoMeal1.setMeal(duzeDanie);
        rhinoMeal2.setMeal(wegetarianskieDanie);

        wyc.dodajCzynnosc(Czynnosc.stworzCzynnosc(wyc, "Karmienie nosorożców"));
        wyc.dodajCzynnosc(Czynnosc.stworzCzynnosc(wyc, "Oglądanie pływających nosorożców"));

        mieso.addMeal(duzeDanie);
        zielenina.addMeal(duzeDanie);
        zielenina.addMeal(wegetarianskieDanie);

        // WYSWIETL INFO O NOSOROZCAH

        System.out.println(jawajski);
        System.out.println(bialy);

        // WYSWIETL INFO O OPIEKUNACH

        System.out.println(rodriges);
        System.out.println(osolejwa);

        System.out.println("NOSOROZEC POSIЈEK"+"\n----------------------");
        System.out.println(rhinoMeal1);
        System.out.println(rhinoMeal2);

        System.out.println(wyc);

        System.out.println(mieso);
        System.out.println(zielenina);
    }
}
