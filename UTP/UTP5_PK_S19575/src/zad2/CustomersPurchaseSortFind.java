/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad2;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CustomersPurchaseSortFind {
    List<Purchase> List;
    ArrayList<Purchase> withoutChanges = new ArrayList<>();
    void readFile(String fname) {
        try {
            List = Files.lines(Paths.get(fname)).map(e->
                    new Purchase(e.split(";")[0],
                            e.split(";")[1],
                            e.split(";")[2],
                            Double.valueOf(e.split(";")[3]),
                            Double.valueOf(e.split(";")[4])))
                    .collect(Collectors.toList());
            withoutChanges.addAll(List);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Collator c = Collator.getInstance(new Locale("pl", "PL"));
    void showSortedBy(String arg){
        if(arg.equals("Nazwiska")){
            System.out.println("Nazwiska");
            List.sort((o1, o2) -> {
                if (o1.getImie_Nazwisko().equals(o2.getImie_Nazwisko()))
                    return o1.Id_Klienta.compareToIgnoreCase(o2.Id_Klienta);
                else
                    return c.compare(o1.getImie_Nazwisko(), o2.getImie_Nazwisko());
            });
            for (Purchase purchase : List) {
                System.out.println(purchase);
            }
        }
        if(arg.equals("Koszty")){
            System.out.println("Koszty");
            List.sort((o1,o2)->{
                if (o1.Koszt!=o2.Koszt)
                    return (int) (o2.Koszt - o1.Koszt);
                else
                    return o1.Id_Klienta.compareToIgnoreCase(o2.Id_Klienta);
            });
            for (Purchase purchase : List) {
                System.out.println(purchase + " (koszt: "+ purchase.Koszt + ")");
            }
        }
    }
    void showPurchaseFor(String id){
        System.out.println("Klient " + id);
        withoutChanges.forEach(purchase -> {
            if(purchase.Id_Klienta.equals(id))
                System.out.println(purchase);
        });
    }
}
