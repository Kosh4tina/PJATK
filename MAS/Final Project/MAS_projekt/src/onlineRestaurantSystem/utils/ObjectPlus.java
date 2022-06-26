package onlineRestaurantSystem.utils;

import java.io.*;
import java.util.*;


public abstract class ObjectPlus implements Serializable {
    private static Map<Class, List<ObjectPlus>> allExtents = new Hashtable<>();

    public ObjectPlus() {
        List extent = null;
        Class theClass = this.getClass();

        if(allExtents.containsKey(theClass)) {
            extent = allExtents.get(theClass);
        }
        else {
            extent = new ArrayList();
            allExtents.put(theClass, extent);
        }

        extent.add(this);
    }

    public static void writeExtents(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allExtents);
    }

    public static void readExtents(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        allExtents = (Hashtable) stream.readObject();
    }

    public static <T>ArrayList<T> returnExtentList(Class theClass) throws Exception
    {
        ArrayList<Object> extentList = new ArrayList<>();

        List extent = null;

        if(allExtents.containsKey(theClass)) {
            extent = allExtents.get(theClass);
        }
        else
        {
            throw new Exception("Unknown class " + theClass);
        }

        for(Object obj : extent) {
            extentList.add(obj);
        }
        return (ArrayList<T>)extentList;
    }
}

