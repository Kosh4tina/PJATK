package zad1;

import java.math.BigDecimal;

public class Divide implements Operation {
    @Override
    public BigDecimal execute(BigDecimal a, BigDecimal b) {
        try {
            return a.divide(b);
        } catch (ArithmeticException e) {
            return a.divide(b, 7, BigDecimal.ROUND_HALF_UP);
        }
    }
}