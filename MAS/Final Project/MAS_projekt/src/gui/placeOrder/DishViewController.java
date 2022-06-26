package gui.placeOrder;

import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import onlineRestaurantSystem.entities.Dish;
import onlineRestaurantSystem.entities.DishList;
import onlineRestaurantSystem.utils.ObjectPlus;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DishViewController
{
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button buttonShowOrder;
    @FXML
    private Button buttonAddToOrder;
    @FXML
    private Button buttonRemoveFromOrder;
    @FXML
    private Label labelItemsInOrder;
    @FXML
    private ListView<Dish> dishListView = new ListView<Dish>();
    private ObservableList<Dish> dishObservableList = FXCollections.observableArrayList();
    @FXML
    private ListView<DishList> dishCopyListView = new ListView<DishList>();
    private ObservableList<DishList> dishListObservableList = FXCollections.observableArrayList();

    public static List<DishList> chosenCopiesList = new ArrayList<>();

    public void buttonAddToOrderOnAction(ActionEvent actionEvent)
    {
        if (actionEvent.getSource().equals(buttonAddToOrder))
        {
            DishList selectedCopy = dishCopyListView.getSelectionModel().getSelectedItem();

            if (selectedCopy != null)
            {
                if (!chosenCopiesList.contains(selectedCopy))
                {
                    chosenCopiesList.add(selectedCopy);
                    labelItemsInOrder.setText(Integer.toString(chosenCopiesList.size()));
                }
            }

            if (chosenCopiesList.size() >= 1)
                buttonShowOrder.setDisable(false);
            else
                buttonShowOrder.setDisable(true);
        }
    }

    public void buttonRemoveFromOrderOnAction(ActionEvent actionEvent)
    {
        if (actionEvent.getSource().equals(buttonRemoveFromOrder))
        {
            DishList selectedCopy = dishCopyListView.getSelectionModel().getSelectedItem();

            if (selectedCopy != null)
            {
                if (chosenCopiesList.contains(selectedCopy))
                {
                    chosenCopiesList.remove(selectedCopy);
                    labelItemsInOrder.setText(Integer.toString(chosenCopiesList.size()));
                }
            }

            buttonShowOrder.setDisable(chosenCopiesList.size() < 1);
        }
    }

    public void buttonShowOrderOnAction(ActionEvent actionEvent)
    {
        if (actionEvent.getSource().equals(buttonShowOrder))
        {
            if (chosenCopiesList.size() >= 1)
                fadeOut("fxml/orderView.fxml", rootPane);
        }
    }

    public void showDishList() throws Exception
    {
        ArrayList<Dish> dishes = ObjectPlus.returnExtentList(Dish.class);
        dishObservableList.addAll(dishes);
        dishListView.setItems(dishObservableList);

        dishListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Dish> observableValue, Dish oldValue, Dish newValue) ->
        {
            Dish selectedItem = dishListView.getSelectionModel().getSelectedItem();

            try
            {
                dishCopyListView.getItems().clear();
                showDishCopyList(selectedItem);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }

    public void showDishCopyList(Dish triggeredDish) throws Exception
    {
        ArrayList<DishList> DishCopies = triggeredDish.returnLinks("posiada");

        dishListObservableList.addAll(DishCopies);

        dishCopyListView.setItems(dishListObservableList);
    }

    public void switchScene(String nextSceneFXML, AnchorPane rootPane)
    {
        try
        {
            Parent nextView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(nextSceneFXML)));
            Scene nextScene = new Scene(nextView);

            Stage currentStage = (Stage) rootPane.getScene().getWindow();
            currentStage.setScene(nextScene);

            System.out.println("fxml loaded: " + nextSceneFXML);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void fadeIn(AnchorPane rootPane)
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
    }

    public void fadeOut(String nextSceneFXML, AnchorPane rootPane)
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        fadeTransition.setOnFinished((ActionEvent actionEvent) ->
        {
            switchScene(nextSceneFXML, rootPane);
        });
        fadeTransition.play();
    }

    @FXML
    public void initialize() throws Exception
    {
        fadeIn(rootPane);

        showDishList();

        buttonShowOrder.setDisable(chosenCopiesList.size() < 1);
    }

}