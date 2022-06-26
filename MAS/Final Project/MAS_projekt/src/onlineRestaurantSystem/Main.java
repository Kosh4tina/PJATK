package onlineRestaurantSystem;

import onlineRestaurantSystem.entities.*;
import onlineRestaurantSystem.enums.FormOfEmployment;
import onlineRestaurantSystem.enums.DishType;
import onlineRestaurantSystem.enums.OrderStatus;
import onlineRestaurantSystem.users.Customer;
import onlineRestaurantSystem.users.Cashier;
import onlineRestaurantSystem.users.MenuCreator;
import onlineRestaurantSystem.users.Manager;
import onlineRestaurantSystem.utils.ObjectPlus;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Main
{
    public final static String extentFile = "src/data.bin";

    public static Customer customer1;
    public static OrderOnline orderOnline1;

    public static List<Dish> dishList = new ArrayList<>();

    public static void main(String[] args) throws Exception
    {
        readExtent();

        List<String> roles = new ArrayList<>();
        roles.add("Packer");
        roles.add("Cashier");
        roles.add("Cleaner");

        Cashier cahier1 = new Cashier("Barbek", "Alefanski", new Address("Miedzynarodowa", "15B", "09-345", "Warszawa", "Mazowieckie"),
                LocalDate.of(2020, 12, 1), 2000, FormOfEmployment.UOP, roles);

        System.out.println(cahier1);
        System.out.println("Full salary: " + cahier1.calculateSalary());

        System.out.println("----------------\n------------------");

        Manager manager1 = new Manager("Jonatan", "Sap", new Address("Miedzynarodowa", "15B", "09-345", "Warszawa", "Mazowieckie"),
                LocalDate.of(2020, 10, 10), 4000, FormOfEmployment.UZ);

        Problem problem = customer1.createProblem("Bad service", "Didn't add additional sauce to the order");

        System.out.println(problem);
        System.out.println();
        manager1.proceedProblem(problem);
        System.out.println();
        System.out.println(problem);

        System.out.println("----------------\n------------------");

        MenuCreator menuCreator1 = new MenuCreator("Gordan", "Ramzi", new Address("Saska", "12A", "09-345", "Warszawa", "Mazowieckie"),
                LocalDate.of(2020, 7, 22), 3000, FormOfEmployment.UOP);

        System.out.println(menuCreator1);

        Dish dish3 = menuCreator1.addDish("Burgers", "Bun, cutlet, vegetables and sauce", DishType.Standard);
        menuCreator1.addDishList(101, "Beef with bacon", 150, dish3);
        menuCreator1.addDishList(102, "Beef with bacon and eggs", 200, dish3);

        System.out.println();
        System.out.println(dish3);
        System.out.println();

        dish3.showLinks("posiada", System.out);

        saveExtent();
    }

    public static void saveExtent() throws Exception
    {
        System.out.println("\n" + "SAVE EXTENT" + "\n");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(extentFile));
        ObjectPlus.writeExtents(out);
        out.close();
    }

    public static void readExtent() throws Exception
    {
        File data = new File(extentFile);

        if (data.exists() && data.length() != 0)
        {
            customer1 = new Customer("Zakhar", "Clock", new Address("Sliska", "12A", "09-345", "Warszawa", "Mazowieckie"),
                    "214-124-124", "Clock@jmail.com");

            orderOnline1 = customer1.createOrder(orderOnline1, LocalDate.now(), OrderStatus.Created);

            customer1.addCreditCard("1234567890123456", 123, "02/22");

            System.out.println("\n" + "READ EXTENT" + "\n");
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(extentFile));
            ObjectPlus.readExtents(in);
            in.close();
        }
        else
        {
            System.out.println("\n" + "CREATE SAMPLE DATA" + "\n") ;

            Dish dish1 = new Dish("French fries", "Slices of potatoes fried directly in oil", DishType.Standard);
            Dish dish2 = new Dish("Drinks", "Cooling drink after a meal", DishType.Standard);

            dishList.add(dish1);
            dishList.add(dish2);

            DishList dishList1 = new DishList(105, "Little fries", 40);
            DishList dishList2 = new DishList(106, "Middle fries", 60);
            DishList dishList3 = new DishList(107, "Big fries", 80);

            DishList dishList4 = new DishList(110, "Coco-Cola", 40);
            DishList dishList5 = new DishList(111, "Monster", 60);
            DishList dishList6 = new DishList(112, "Sprite", 20);

            dish1.addLinkDishList(dishList1);
            dish1.addLinkDishList(dishList2);
            dish1.addLinkDishList(dishList3);
            dish2.addLinkDishList(dishList4);
            dish2.addLinkDishList(dishList5);
            dish2.addLinkDishList(dishList6);

            customer1 = new Customer("Zakhar", "Clock", new Address("Sliska", "12A", "09-345", "Warszawa", "Mazowieckie"),
                    "214-124-124", "Clock@jmail.com");

            orderOnline1 = customer1.createOrder(orderOnline1, LocalDate.now(), OrderStatus.Created);

            customer1.addCreditCard("1234567890123456", 123, "02/22");
        }
    }

}