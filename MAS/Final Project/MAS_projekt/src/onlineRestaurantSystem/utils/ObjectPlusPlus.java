package onlineRestaurantSystem.utils;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.*;


public abstract class ObjectPlusPlus extends ObjectPlus implements Serializable {
    private Map<String, Map<Object, ObjectPlusPlus>> links = new Hashtable<>();

    private static Set<ObjectPlusPlus> allParts = new HashSet<>();

    public ObjectPlusPlus() {
        super();
    }

    private void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject, Object qualifier, int counter) {
        Map<Object, ObjectPlusPlus> objectLinks;

        if(counter < 1) {
            return;
        }

        if(links.containsKey(roleName)) {
            objectLinks = links.get(roleName);
        }
        else {
            objectLinks = new HashMap<>();
            links.put(roleName, objectLinks);
        }


        if(!objectLinks.containsKey(qualifier)) {
            objectLinks.put(qualifier, targetObject);

            targetObject.addLink(reverseRoleName, roleName, this, this, counter - 1);
        }
    }

    public void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject, Object qualifier) {
        addLink(roleName, reverseRoleName, targetObject, qualifier, 2);
    }

    public void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject) {
        addLink(roleName, reverseRoleName, targetObject, targetObject);
    }

    public void addPart(String roleName, String reverseRoleName, ObjectPlusPlus partObject) throws Exception {
        if(allParts.contains(partObject)) {
            throw new Exception("The part is already connected to a whole!");
        }

        addLink(roleName, reverseRoleName, partObject);

        allParts.add(partObject);
    }

    public void showLinks(String roleName, PrintStream stream) throws Exception {
        Map<Object, ObjectPlusPlus> objectLinks;
        List<Object> extentList = new ArrayList<>();

        if(!links.containsKey(roleName)) {

            throw new Exception("No links for the role: " + roleName);
        }

        objectLinks = links.get(roleName);

        Collection col = objectLinks.values();

        stream.println(this.getClass().getSimpleName() + " links, role '" + roleName + "':");

        for(Object obj : col)
        {
            extentList.add(obj);
            stream.println("   " + obj);
        }
    }

    public <T>ArrayList<T> returnLinks(String roleName) throws Exception
    {
        Map<Object, ObjectPlusPlus> objectLinks;
        ArrayList<Object> extentList = new ArrayList<>();

        if(!links.containsKey(roleName)) {
            throw new Exception("No links for the role: " + roleName);
        }

        objectLinks = links.get(roleName);

        Collection col = objectLinks.values();

        for(Object obj : col)
        {
            extentList.add(obj);
        }

        return (ArrayList<T>)extentList;
    }

    public boolean isLink(String roleName) {

        if(!links.containsKey(roleName)) {
            return true;
        }
        return false;
    }
}