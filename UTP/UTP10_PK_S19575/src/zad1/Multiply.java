package zad1;

import java.math.BigDecimal;

public class Multiply implements Operation {
    @Override
    public BigDecimal execute(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }
}