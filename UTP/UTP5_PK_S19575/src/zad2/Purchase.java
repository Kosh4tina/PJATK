/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad2;


public class Purchase {
    String Id_Klienta;
    String Imie_Nazwisko;
    String Nazwa_Towaru;
    double Cena, Zakupiona_Ilosc, Koszt;

    public Purchase(String Id_klienta, String Nazwisko_Imie, String Nazwa_Towaru, double Cena, double Zakupiona_Ilosc) {
        this.Id_Klienta = Id_klienta;
        this.Imie_Nazwisko = Nazwisko_Imie;
        this.Nazwa_Towaru = Nazwa_Towaru;
        this.Cena = Cena;
        this.Zakupiona_Ilosc = Zakupiona_Ilosc;
        Koszt=Cena*Zakupiona_Ilosc;
        //warto uwzgledniac polkie litery
    }
    String getImie_Nazwisko(){
        return Imie_Nazwisko;
    }

    @Override
    public String toString() {
        return Id_Klienta+";"+Imie_Nazwisko+";"+Nazwa_Towaru+";"+Cena+";"+Zakupiona_Ilosc;
    }
}
