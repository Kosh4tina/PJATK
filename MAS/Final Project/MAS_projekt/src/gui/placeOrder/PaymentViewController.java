package gui.placeOrder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import onlineRestaurantSystem.entities.Payment;
import onlineRestaurantSystem.enums.OrderStatus;
import java.time.LocalDate;

public class PaymentViewController extends DishViewController
{
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button buttonPay;
    @FXML
    public TextField textFieldCodeCVC;
    @FXML
    public TextField textFieldCreditCardNumber;
    @FXML
    public TextField textFieldExpirationDate;
    @FXML
    public Label labelValidation1;
    @FXML
    public Label labelValidation2;
    @FXML
    public Label labelValidation3;

    public void buttonPayOnAction(ActionEvent actionEvent) throws Exception
    {
        if (actionEvent.getSource().equals(buttonPay))
        {
            if (String.valueOf(textFieldCreditCardNumber.getText()).length() == 16)
            {
                labelValidation1.setVisible(false);
                if (String.valueOf(textFieldCodeCVC.getText()).length() == 3)
                {
                    labelValidation1.setVisible(false);
                    labelValidation2.setVisible(false);
                    if (String.valueOf(textFieldExpirationDate.getText()).length() == 5)
                    {
                        labelValidation1.setVisible(false);
                        labelValidation2.setVisible(false);
                        labelValidation3.setVisible(false);

                        Main_gui.orderOnline.setOrderStatus(OrderStatus.Paid);
                        fadeOut("fxml/paymentConfirmation.fxml", rootPane);
                    }
                    else
                        {
                            labelValidation3.setVisible(true);
                            labelValidation3.setText("Expiration date must have 5 digits");
                        }
                }
                else
                    {
                        labelValidation2.setVisible(true);
                        labelValidation2.setText("CVC code must have 3 digits");
                    }
            }
            else
                {
                    labelValidation1.setVisible(true);
                    labelValidation1.setText("Card number must have 16 digits");
                }
        }
    }

    @FXML
    public void initialize() throws Exception
    {
        fadeIn(rootPane);

        if (Main_gui.customer.hasCreditCard())
        {
            textFieldCreditCardNumber.setText(Main_gui.customer.returnCreditCardNumber());
            textFieldCodeCVC.setText(Integer.toString(Main_gui.customer.returnCreditCardCodeCVC()));
            textFieldExpirationDate.setText(Main_gui.customer.returnCreditCardExpirationDate());
        }

        labelValidation1.setVisible(false);
        labelValidation2.setVisible(false);
        labelValidation3.setVisible(false);
    }

}