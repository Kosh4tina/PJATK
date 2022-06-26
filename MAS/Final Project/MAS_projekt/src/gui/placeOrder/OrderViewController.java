package gui.placeOrder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import onlineRestaurantSystem.entities.Dish;
import onlineRestaurantSystem.entities.DishList;
import onlineRestaurantSystem.entities.OrderOnline;

import java.util.ArrayList;
import java.util.List;

public class OrderViewController extends DishViewController
{
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button buttonPlaceOrder;
    @FXML
    private Button buttonBack;
    @FXML
    private Label labelItemsInOrder;
    @FXML
    private Label labelFinalPrice;
    @FXML
    private ListView<DishList> listViewFromOrder = new ListView<DishList>();
    private ObservableList<DishList> dishCopiesFromOrder = FXCollections.observableArrayList();

    public void buttonPlaceOrderOnAction(ActionEvent actionEvent)
    {
        if (actionEvent.getSource().equals(buttonPlaceOrder))
            fadeOut("fxml/paymentView.fxml", rootPane);
    }

    public void buttonBackOnAction(ActionEvent actionEvent)
    {
        if (actionEvent.getSource().equals(buttonBack))
        {
            fadeOut("fxml/dishesView.fxml", rootPane);
            chosenCopiesList.clear();
        }
    }

    public void addChosenCopiesToOrder(OrderOnline orderOnline) throws Exception
    {
        int finalPrice = 0;
        List<Dish> dishes = new ArrayList<Dish>();

        for (DishList dishList : chosenCopiesList)
        {
            orderOnline.addLinkDishCopy(dishList);
            finalPrice += dishList.getPrice();
            dishes.addAll(dishList.returnLinks("zawiera sie"));
        }

        orderOnline.setFinalPrice(finalPrice);
    }

    @FXML
    public void initialize() throws Exception
    {
        fadeIn(rootPane);

        addChosenCopiesToOrder(Main_gui.orderOnline);

        dishCopiesFromOrder.addAll(chosenCopiesList);
        listViewFromOrder.setItems(dishCopiesFromOrder);

        labelItemsInOrder.setText(Integer.toString(chosenCopiesList.size()));
        labelFinalPrice.setText(Integer.toString(Main_gui.orderOnline.getFinalPrice()));
    }
}