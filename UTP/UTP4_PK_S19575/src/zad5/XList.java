package zad5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class XList<T> extends ArrayList<T> {

    public XList() {
    }

    public XList(T ... args) {
        this.addAll(XList.of(args));
    }

    public static <T> XList of(T ... args) {
        boolean isCollection = false;
        if (args.length > 1) {
            isCollection = true;
            for (Object object : args) {
                if (!(object instanceof Collection || object.getClass().isArray())) {
                    isCollection = false;
                    break;
                }
            }
        }
        XList tmp = new XList<>();
        for (Object object : args) {
            if (object instanceof Collection && !isCollection) {
                ((Collection) object).forEach(o -> tmp.addAll(XList.of(o)));
            } else if (object.getClass().isArray() && !isCollection) {
                Arrays.stream(((Object[]) object)).forEach(o -> tmp.addAll(XList.of(o)));
            } else {
                if (isCollection) {
                    tmp.add(XList.of(object));
                } else {
                    tmp.add(object);
                }
            }
        }
        return tmp;
    }

    public static XList<String> charsOf(String a) {
        XList l = new XList();
        for (Character c : a.toCharArray()) {
            l.add(c);
        }
        return l;
    }

    public static XList<String> tokensOf(String ... args) {
        if(args.length==1)
            return XList.of(args[0].split("\\s+"));
        else
            return XList.of(args[0].split(args[1]));
    }

    public <R>XList<T> union(R ...set) {
        XList tmp = new XList(this);
        tmp.addAll(XList.of(set));
        return tmp;
    }

    public <R>XList<T> diff(R  ... set) {
        XList tmp = new XList(this);
        tmp.removeAll(XList.of(set));
        return tmp;
    }

    public XList<T> unique() {
        XList tmp = new XList();
        this.forEach(o -> {
            if (!tmp.contains(o))
                tmp.add(o);
        });
        return tmp;
    }

    public XList<XList<T>> combine() {
        XList<XList<T>> toCombine = (XList<XList<T>>)this;
        XList<XList<T>> xList = new XList<>();
        int sum=1;
        for (XList<T> sub: toCombine) {
            sum*=sub.size();
        }
        for (int i=0;i<sum;i++){
            xList.add(new XList<>());
        }
        int arr[] = new int[toCombine.size()];
        for (int j=0;j<sum;j++){
            for(int i=0;i<arr.length;i++) {
                xList.get(j).add(toCombine.get(i).get(arr[i]));
            }

            for(int i=0;i<arr.length;i++) {
                if(arr[i]<toCombine.get(i).size()-1) {
                    arr[i]++;
                    break;
                }else{
                    arr[i]=0;
                }
            }
        }
        return xList;
    }

    public String join() {
        return join("");

    }
    public String join(String sep) {
        return this.stream()
                .map(Object::toString)
                .collect( Collectors.joining(sep));
    }

    public <R>XList<String> collect (Function<XList<R>, String> func){
        XList xList = new XList();
        for (XList<R> t : ((XList<XList<R>>) this)) {
            xList.add(func.apply(t));
        }
        return xList;
    }
    public void forEachWithIndex(BiConsumer<T, Integer> biCon){
        for (int i = 0; i < this.size(); i++) {
            biCon.accept(this.get(i), i);
        }
    }
}
