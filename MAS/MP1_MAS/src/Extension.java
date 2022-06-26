import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class Extension implements Serializable
{
    private static final long serialVersionUID = 1L;

    //zbiór aktualnie występujących obiektów danej klasy
    private static Hashtable<Class<? extends Extension>, Vector<Extension>> ekstensje = new Hashtable<>();

    public Extension(){
        Vector<Extension> ekstensja;
        Class<? extends Extension> klasa = this.getClass();

        if(ekstensje.containsKey(klasa))
        {
            ekstensja = ekstensje.get(klasa);
        }
        else
        {
            ekstensja = new Vector<>();
            ekstensje.put(klasa, ekstensja);
        }
        ekstensja.add(this);
    }

    public static void saveExtension(ObjectOutputStream stream) throws IOException
    {
        stream.writeObject(ekstensje);
    }

    public static void readExtension(ObjectInputStream stream) throws IOException, ClassNotFoundException
    {
        ekstensje = (Hashtable<Class<? extends Extension>, Vector<Extension>>) stream.readObject();
    }

    public static void showExtension(Class<? extends Extension> klasa) throws Exception
    {
        Vector<Extension> ekstensja;

        if(ekstensje.containsKey(klasa))
        {
            ekstensja = ekstensje.get(klasa);
        }
        else
        {
            throw new Exception("Unknown class " + klasa);
        }

        System.out.println("Class of extension: " + klasa.getSimpleName());

        for(Object obiekt : ekstensja)
        {
            System.out.println(obiekt + "\n");
        }
    }
}