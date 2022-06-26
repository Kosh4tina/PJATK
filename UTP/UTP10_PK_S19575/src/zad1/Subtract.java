package zad1;

import java.math.BigDecimal;

public class Subtract implements Operation {
    @Override
    public BigDecimal execute(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }
}