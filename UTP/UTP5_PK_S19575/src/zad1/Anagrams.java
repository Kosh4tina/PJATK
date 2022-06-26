/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Anagrams {
    String URL;
    ArrayList<String> tmpList;
    ArrayList<ArrayList<String>> Anagrams;
    ArrayList<Integer> hashCodesList;
    Anagrams(String URL){
        this.URL = URL;
        tmpList = new ArrayList<>();
        Anagrams = new ArrayList<>();
        hashCodesList = new ArrayList<>();
        try {
            ArrayList<String> tmp = new ArrayList<>();
            Scanner scan = new Scanner(new File(URL));
            while (scan.hasNext())
                tmpList.add(scan.next());
            for (String s : tmpList) {
                if (!hashCodesList.contains(myHashCode(s))) {
                    hashCodesList.add(myHashCode(s));
                }
            }
            for (int i = 0; i < hashCodesList.size(); i++) {
                Integer integer = hashCodesList.get(i);
                for (int j = 0; j < tmpList.size(); j++) {
                    String s = tmpList.get(j);
                    if (integer == myHashCode(s))
                        tmp.add(s);
                }
                Anagrams.add(tmp);
                tmp = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<String>> getSortedByAnQty() {
        Anagrams.sort(((o1, o2) -> o2.size()-o1.size()));
        Anagrams.forEach(e->e.sort(Comparator.naturalOrder()));
        return Anagrams;
    }

    public String getAnagramsFor(String word){
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < Anagrams.size(); i++) {
            for (int j = 0; j < Anagrams.get(i).size(); j++) {
                if((myHashCode(word)==myHashCode(Anagrams.get(i).get(j))) && !word.equals(Anagrams.get(i).get(j)))
                    result.add(Anagrams.get(i).get(j));
            }
        }
        result.sort(Comparator.naturalOrder());
        return word + ": " + result;
    }
    int myHashCode(String s){
        char[] tmp = s.toCharArray();
        int sum = 0;
        for (int i = 0; i < tmp.length; i++) {
            sum+=tmp[i];
        }
        return sum;
    }
}  
