/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;


import java.util.*;

public class Main {

  static List<String> getPricesInPLN(List<String> destinations, double xrate) {
    return ListCreator.collectFrom(destinations)
                       .when( l-> {  String p = (String)l;
                          if(p.startsWith("WAW"))
                            return true;
                          else
                            return false;} /*<-- lambda wyrażenie
                                *  selekcja wylotów z Warszawy (zaczynających się od WAW)
                                */
                       )
                       .mapEvery(l -> {  String p = (String)l;
                         return "to "+p.split(" ")[1]+" - price in PLN:  "+Integer.parseInt(p.split(" ")[2])*xrate;} /*<-- lambda wyrażenie
                                   *  wyliczenie ceny przelotu w PLN
                                   *  i stworzenie wynikowego napisu
                                   */
                        );
  }

  public static void main(String[] args) {
    // Lista destynacji: port_wylotu port_przylotu cena_EUR 
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    List<String> result = getPricesInPLN(dest, ratePLNvsEUR);
    for (String r : result) System.out.println(r);
  }
}
