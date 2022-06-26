package gui.placeOrder;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import onlineRestaurantSystem.entities.Address;
import onlineRestaurantSystem.entities.Dish;
import onlineRestaurantSystem.entities.DishList;
import onlineRestaurantSystem.entities.OrderOnline;
import onlineRestaurantSystem.enums.DishType;
import onlineRestaurantSystem.enums.OrderStatus;
import onlineRestaurantSystem.users.Customer;
import onlineRestaurantSystem.utils.ObjectPlus;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main_gui extends Application
{
    public static Customer customer;
    public static OrderOnline orderOnline;

    public static List<Dish> dishList = new ArrayList<>();

    public final static String extentFile = "src/data.bin";

    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/dishesView.fxml"))); // load scene from fxml
        primaryStage.setTitle("Online Orders Service System");
        Scene scene = new Scene(root);
        primaryStage.setMinWidth(600);
        primaryStage.setMaxWidth(600);
        primaryStage.setMinHeight(438);
        primaryStage.setMaxHeight(438);
        primaryStage.setScene(scene);
        primaryStage.show();


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent event)
            {
                Platform.exit();
                try
                {
                    saveExtent();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
                System.exit(0);
            }
        });
    }

    public static void main(String[] args)
    {
        try
        {
            readExtent();
            launch(args);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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
            customer = new Customer("Zakhar", "Clock", new Address("Sliska", "12A", "09-345", "Warszawa", "Mazowieckie"),
                    "214-124-124", "Clock@jmail.com");

            orderOnline = customer.createOrder(orderOnline, LocalDate.now(), OrderStatus.Created);

            customer.addCreditCard("1234567890123456", 123, "02/22");

            System.out.println("\n" + "READ EXTENT" + "\n");
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(extentFile));
            ObjectPlus.readExtents(in);
            in.close();
        }
        else
        {
            System.out.println("\n" + "CREATE SAMPLE DATA" + "\n");

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

            customer = new Customer("Zakhar", "Clock", new Address("Sliska", "12A", "09-345", "Warszawa", "Mazowieckie"),
                    "214-124-124", "Clock@jmail.com");

            orderOnline = customer.createOrder(orderOnline, LocalDate.now(), OrderStatus.Created);

            customer.addCreditCard("1234567890123456", 123, "02/22");

        }
    }

}