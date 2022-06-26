/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import java.util.ArrayList;
import java.util.List;

public class ListCreator {
    private List list;
    private List when = new ArrayList();
    private List mapE = new ArrayList();

    private ListCreator(List nl){
        this.list=nl;
    }

    static ListCreator collectFrom(List nl){
        return new ListCreator(nl);
    }

    ListCreator when(Selector S){
        for (Object e : list) {
            if(S.Select(e)){
                when.add(e);
            }
        }
        return this;
    }

    List mapEvery(Mapper M){
        for (Object e : when) {
            mapE.add(M.Map(e));
        }
        return mapE;
    }
}

