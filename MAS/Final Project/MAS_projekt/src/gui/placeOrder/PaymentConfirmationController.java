package gui.placeOrder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class PaymentConfirmationController extends DishViewController
{
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button buttonOk;

    public void buttonOkOnAction(ActionEvent actionEvent) throws Exception
    {
        if (actionEvent.getSource().equals(buttonOk))
        {
            System.out.println("closing application...");

            Main_gui.saveExtent();
            System.exit(0);
        }
    }

    @FXML
    public void initialize()
    {
        fadeIn(rootPane);
    }

}