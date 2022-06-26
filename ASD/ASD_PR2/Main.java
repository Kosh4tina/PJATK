import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        long Sum = 0;
        String InputRow;

        ArrayList<Integer> UpRow = new ArrayList<>();
        ArrayList<Integer> DownRow = new ArrayList<>();
        ArrayList<Integer> UpRow1 = new ArrayList<>();
        ArrayList<Integer> DownRow1 = new ArrayList<>();
        ArrayList<Integer> Row = new ArrayList<>();

        System.out.println("Example (1 2 3 4 5 1 2 3 1 2 3 4 5 6)");
        System.out.println();
        System.out.println("Input Row: ");

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            InputRow = bufferedReader.readLine();
            Pattern pattern = Pattern.compile("[0-9]+");
            Matcher matcher = pattern.matcher(InputRow);
            while (matcher.find()) {
                Row.add(Integer.parseInt(matcher.group()));
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DownRow.add(Row.get(0));
        UpRow.add(Row.get(0));
        DownRow1.add(Row.get(0));
        UpRow1.add(Row.get(0));

        for (int i = 1; i < Row.size(); i++) {
            int number = Row.get(i);
//down
            if (number <= DownRow.get(DownRow.size() - 1)) {
                DownRow.add(number);
            } else {
                DownRow.removeAll(DownRow);
                DownRow.add(number);
            }

            if (DownRow.size() > DownRow1.size()) {
                DownRow1.removeAll(DownRow1);
                DownRow1.addAll(DownRow);
            }
//up
            if (number >= UpRow.get(UpRow.size() - 1)) {
                UpRow.add(number);
            } else {
                UpRow.removeAll(UpRow);
                UpRow.add(number);
            }

            if (UpRow.size() > UpRow1.size()) {
                UpRow1.removeAll(UpRow1);
                UpRow1.addAll(UpRow);
            }
        }

        if (UpRow1.size() > DownRow1.size()) {
            for (Integer integer : UpRow1) {
                Sum += integer;
            }
            System.out.println("Size = " + UpRow1.size() + ", Sum = " + Sum);
        } else if (UpRow1.size() < DownRow1.size()) {
            for (Integer integer : DownRow1) {
                Sum += integer;
            }
            System.out.println("Size = " + DownRow1.size() + ", Sum = " + Sum);
        }
    }
}