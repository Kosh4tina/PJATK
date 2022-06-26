package onlineRestaurantSystem.entities;

import onlineRestaurantSystem.utils.ObjectPlusPlus;
import java.util.HashMap;
import java.util.Map;


/**
 * This class stores data about products.
 *
 * Linked with:
 * 1. Dish with cardinality 0..* - 1
 * 2. Order with cardinality 1..* - 0..1
 */
public class DishList extends ObjectPlusPlus
{
    private int numer; // unique value
    private String product;
    private int price;
    private static Map<Integer, DishList> dishListHashMap = new HashMap<>();

    public DishList(int numer, String product, int price) throws Exception
    {
        setNumer(numer);
        this.price = price;
        this.product = product;
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) throws Exception
    {
        if (!dishListHashMap.containsKey(numer))
        {
            if (String.valueOf(numer).length() == 3)
            {
                dishListHashMap.put(numer, this);
                this.numer = numer;
            }
            else
                throw new Exception("Id number must have 3 digits!");
        }
        else
            throw new Exception("Product with this id number is already exist!");
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString()
    {
        return "Product: " + getProduct() + "\nPrice: " + getPrice();
    }

}