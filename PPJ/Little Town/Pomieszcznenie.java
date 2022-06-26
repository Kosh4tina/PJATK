import functions.NotUniqueValueEX;

import java.util.Set;
import java.util.TreeSet;

public abstract class Pomieszcznenie{
    public static int count = 0;
    public int id;
    double rozmiarPowierchni;
    public double freeRozmiar;

    private static final Set<String> id_unique = new TreeSet<>();

    public Pomieszcznenie(double rozmiarPowierchni){
        setId(++count);
        setRozmiar(rozmiarPowierchni);
    }

    public Pomieszcznenie(double dlugosc, double sherokosc, double wysokosc){
        setId(++count);
        setRozmiar(dlugosc, sherokosc, wysokosc);
    }

    public void setId(int id) throws NotUniqueValueEX {
        if(idUsed(id))
            throw new NotUniqueValueEX();
        else this.id = id;
    }

    public void setRozmiar(double rozmiarPowierchni) {
        this.rozmiarPowierchni = rozmiarPowierchni;
        freeRozmiar = rozmiarPowierchni;
    }

    public void setRozmiar(double dlugosc, double sherokosc, double wysokosc){
        double rozmiar = 2* ((dlugosc*sherokosc) + (sherokosc*wysokosc) + (wysokosc*dlugosc));
        this.rozmiarPowierchni = rozmiar;
        freeRozmiar = rozmiar;
    }

    public void changeRozmiarMin(double size){
        this.freeRozmiar = freeRozmiar-size;
    }

    public void changeRozmiarAdd(double size){
        this.freeRozmiar = freeRozmiar+size;
    }

    private static boolean idUsed(int id){
        return id_unique.contains(id);
    }

    public int getId() {
        return id;
    }

    public double getRozmiarPowierchni() {
        return rozmiarPowierchni;
    }

    public double getFreeRozmiar() {
        return freeRozmiar;
    }
}
