package onlineRestaurantSystem.users;

import onlineRestaurantSystem.entities.Address;
import onlineRestaurantSystem.entities.Dish;
import onlineRestaurantSystem.entities.DishList;
import onlineRestaurantSystem.enums.FormOfEmployment;
import onlineRestaurantSystem.enums.DishType;
import java.time.LocalDate;
import java.util.List;


/**
 * This class stores data about menuCreator.
 * menuCreator can add, edit and delete dishes
 *
 * Inherits from Employee
 *
 * Linked with:
 * 1. Dish with cardinality 1 - 1..*
 */
public class MenuCreator extends Employee
{
    public MenuCreator(String name, String surname, Address address,
                       LocalDate employmentDate, int baseSalary, FormOfEmployment formOfEmployment) throws Exception
    {
        super(name, surname, address, employmentDate, baseSalary, formOfEmployment);
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    public void addLinkDish(Dish dish)
    {
        this.addLink("edits","is edited", dish);
    }

    public Dish addDish(String name, String description, DishType dishType) throws Exception
    {
        Dish newDish = new Dish(name, description, dishType);
        this.addLinkDish(newDish);

        return newDish;
    }

    public void deleteDish(Dish dishToDelete, List<Dish> dishList)
    {
        dishList.remove(dishToDelete);
    }

    public DishList addDishList(int id, String product, int price, Dish dish) throws Exception
    {
        DishList newDishList = new DishList(id, product, price);
        dish.addLinkDishList(newDishList);

        return newDishList;
    }

}