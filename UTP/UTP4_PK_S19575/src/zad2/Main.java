/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad2;


import zad1.InputConverter;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/*<-- niezbędne import */
public class Main {
  public static void main(String[] args) throws IOException {
    TFunction<String, List<String>> flines = (f) -> {
      List<String> result = new ArrayList<>();
      Scanner sc = new Scanner(new FileReader(f));
      while (sc.hasNext()){
        result.add(sc.nextLine());
      }
      return result;
    };
    Function<List<String>, String> join= f -> {
      List<String> tmp = (List<String>) f;
      String result="";
      for(String t : tmp)
        result+=t;
      return result;
    };
    Function<String,List<Integer>> collectInts= f -> {
      List<Integer> result = new ArrayList<>();
      String tmp = f.toString();
      String tmpInt = "";
      for(char t : tmp.toCharArray()){
        if(Character.isDigit(t))
          tmpInt+=t;
        else{
          if(tmpInt.length()>0){
            result.add(Integer.parseInt(tmpInt));
            tmpInt="";
          }
        }
      }
      if(tmpInt.length()>0)
        result.add(Integer.parseInt(tmpInt));
      return result;
    };
    Function<List<Integer>, Integer> sum= f -> {
      List<Integer> tmp = f;
      int result=0;
      for(int i : tmp) {
        result += i;
      }
      return result;
    };
    /*<--
     *  definicja operacji w postaci lambda-wyrażeń:
     *  - flines - zwraca listę wierszy z pliku tekstowego
     *  - join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)
     *  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
     *  - sum - zwraca sumę elmentów listy liczb całkowitych
     */

    String fname = System.getProperty("user.home") + "/LamComFile.txt"; 
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
    String text = fileConv.convertBy(flines, join);
    List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
    Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

    System.out.println(lines);
    System.out.println(text);
    System.out.println(ints);
    System.out.println(sumints);

    List<String> arglist = Arrays.asList(args);
    InputConverter<List<String>> slistConv = new InputConverter<>(arglist);  
    sumints = slistConv.convertBy(join, collectInts, sum);
    System.out.println(sumints);

    // Zadania badawcze:
    // Operacja flines zawiera odczyt pliku, zatem może powstac wyjątek IOException
    // Wymagane jest, aby tę operację zdefiniowac jako lambda-wyrażenie
    // Ale z lambda wyrażeń nie możemy przekazywac obsługi wyjatków do otaczającego bloku
    // I wobec tego musimy pisać w definicji flines try { } catch { }
    // Jak spowodować, aby nie było to konieczne i w przypadku powstania wyjątku IOException
    // zadziałała klauzula throws metody main
  }
}
