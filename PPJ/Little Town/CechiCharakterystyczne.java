import functions.NotUniqueValueEX;

import java.util.Set;
import java.util.TreeSet;

public class CechiCharakterystyczne {
    public static int count = 0;
    int id;
    String nazwaCechi;
    String wartoscCechi;

    private static final Set<String> id_unique = new TreeSet<>();

    public CechiCharakterystyczne(String nazwaCechi, String wartoscCechi){
        setId(++count);
        this.nazwaCechi = nazwaCechi;
        this.wartoscCechi = wartoscCechi;
    }

    public void setId(int id) throws NotUniqueValueEX {
        if(idUsed(id))
            throw new NotUniqueValueEX();
        else this.id = id;
    }

    public String getNazwaCechi() {
        return nazwaCechi;
    }

    public String getWartoscCechi() {
        return wartoscCechi;
    }

    private static boolean idUsed(int id){
        return id_unique.contains(id);
    }
}
