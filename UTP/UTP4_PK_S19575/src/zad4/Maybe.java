package zad4;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {
    T o;

    Maybe(T o) {
        this.o = o;
    }

    static public <T> Maybe<T> of(T o) {
        return new Maybe<T>(o);
    }

    void ifPresent(Consumer<T> cons) {
        if (isPresent()) {
            cons.accept(this.o);
        } else {
        }
    }

    <R> Maybe<R> map(Function<T, R> func) {
        if (isPresent()) {
            return new Maybe<R>(func.apply(o));
        } else {
            return new Maybe<R>(null);
        }
    }

    T get() {
        if (isPresent()) {
            return o;
        } else {
            throw new NoSuchElementException("maybe is empty");
        }


    }

    boolean isPresent() {
        return (o != null);
    }

    T orElse(T defVal) {
        if (isPresent()) {
            return o;
        } else {
            return defVal;
        }
    }

    Maybe<T> filter(Predicate<T> pred) {
        if (!isPresent() || pred.test(o))
            return new Maybe<T>(o);
        else
            return new Maybe<T>(null);
    }

    @Override
    public String toString() {
        if (isPresent()) {
            return "Maybe has value " + o.toString();
        } else {
            return "Maybe is empty";
        }
    }
}
