package zad1;

import java.util.function.Function;

public class InputConverter<T> {

    private T value = null;

    public InputConverter(T t) {
        value = t;
    }

    @SuppressWarnings({ "hiding", "unchecked", "rawtypes" })
    public <T, R> R convertBy(Function... functions) {
        Object result = value;
        for (int i = 0; i < functions.length; i++) {
            result = functions[i].apply(result);
        }
        return (R) result;
    }

}