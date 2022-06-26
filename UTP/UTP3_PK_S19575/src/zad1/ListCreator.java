package zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListCreator<A> {
    private List<A> list;

    private ListCreator(List<A> List){
        this.list = List;
    }

    static ListCreator collectFrom(List destinations){
        return new ListCreator(destinations);
    }

    List<A> mapEvery(Function<A,Integer> b){
        List result = new ArrayList();
        for(A e: list){
            result.add(b.apply(e));
        }
        return result;
    }

    ListCreator<A> when(Predicate<A> c) {
        List result = new ArrayList();
        for(A e : list){
            if(c.test(e)){
                result.add(e);
            }
        }
        list=result;
        return this;
    }
}
