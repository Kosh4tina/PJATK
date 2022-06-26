package zad1;

import java.math.BigDecimal;

public class Add implements Operation {
    @Override
    public BigDecimal execute(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }
}