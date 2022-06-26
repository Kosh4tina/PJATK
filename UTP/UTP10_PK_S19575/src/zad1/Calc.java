/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Calc {
    private static Map<String, Operation> OPERATIONS;

    static {
        OPERATIONS = new HashMap<>();

        OPERATIONS.put("+", new Add());
        OPERATIONS.put("-", new Subtract());
        OPERATIONS.put("/", new Divide());
        OPERATIONS.put("*", new Multiply());
    }

    String doCalc(String cmd) {
        try {
            String[] args = cmd.split("[\\s]+");

            BigDecimal result = Calc.OPERATIONS.get(args[1]).execute(new BigDecimal(args[0]), new BigDecimal(args[2]));

            return result.toString().replaceAll(",", ".");
        } catch(Exception exception) {
            return "Invalid command";
        }
    }

}