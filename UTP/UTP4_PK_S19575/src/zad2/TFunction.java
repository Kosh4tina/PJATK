package zad2;

import java.io.IOException;
import java.util.function.Function;

public interface TFunction<T,R> extends java.util.function.Function<T,R> {
    R applyThrow(T a) throws IOException;

    @Override
    default R apply(T o) {
        try {
            return applyThrow(o);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
