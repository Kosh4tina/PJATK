/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*<--
 *  niezbędne importy
 */
public class Main {
  public static void main(String[] args) {
    Function<String, List<String>> flines = x -> {
      List<String> listTmp = new ArrayList<String>();
      try {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(x));
        String s = "";
        while ((s = bufferedReader.readLine()) != null) {
          listTmp.add(s);
        }
        bufferedReader.close();
      } catch (IOException ex) {
      }
      return listTmp;
    };

    Function<List<String>, String> join = x -> {
      StringBuilder strBldr = new StringBuilder();
      x.forEach(y -> strBldr.append(y));
      return strBldr.toString();
    };

    Function<String, List<Integer>> collectInts = x -> {
      List<Integer> tmp = new ArrayList<Integer>();
      Matcher m = Pattern.compile("\\d+").matcher(x.toString());
      while (m.find()) {
        tmp.add(Integer.valueOf(m.group()));
      }
      return tmp;
    };

    Function<List<Integer>, Integer> sum = x -> x.stream().mapToInt(Integer::intValue).sum();

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

  }
}
