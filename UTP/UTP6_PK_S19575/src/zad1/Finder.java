/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Finder {
    List<String> file = new ArrayList<>();

    public Finder(String fname) throws FileNotFoundException {
        Scanner in = new Scanner(new File(fname));
        while (in.hasNext()){
            file.add(in.nextLine());
        }
    }

    public int getIfCount() {
        List<String> tmpFile = file;
        return tmpFile.stream().mapToInt(str -> (int) Arrays.stream(str.split(" "))
                .filter(str1 -> str1.startsWith("if")).count()).sum();
    }

    public int getStringCount(String word) {
        return file.stream().mapToInt(str -> (int) Arrays.stream(str.split(" "))
                .filter(str1 -> str1.contains(word)).count()).sum();

    }

}    
