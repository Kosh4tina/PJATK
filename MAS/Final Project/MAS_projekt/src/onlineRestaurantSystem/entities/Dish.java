package onlineRestaurantSystem.entities;

import onlineRestaurantSystem.enums.DishType;
import onlineRestaurantSystem.utils.ObjectPlusPlus;

/**
 * This class stores data about dish.
 *
 * Linked with:
 * 1. MenuCreator with cardinality 1..* - 1
 * 2. DishList with cardinality 1 - 0..*
 */
public class Dish extends ObjectPlusPlus
{
    private String name;
    private String description;
    private int quantity = 0;
    private DishType dishType;

    public Dish(String name, String description, DishType dishType) throws Exception
    {
        this.name = name;
        this.description = description;
        setDishType(dishType);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) throws Exception
    {
        if (dishType.equals(DishType.Standard) || dishType.equals(DishType.Vege) || dishType.equals(DishType.Glutenfree) || dishType.equals(DishType.Spice))
            this.dishType = dishType;
        else
            throw new Exception("There is no such dish type!");
    }

    @Override
    public String toString()
    {
        return getName() + " \n" + getDescription() + "\n"
                + "Num. of positions: " + getQuantity();
    }

    public void addLinkDishList(DishList dishList)
    {
        this.addLink("posiada","zawiera sie", dishList);

        quantity += 1;
    }

}