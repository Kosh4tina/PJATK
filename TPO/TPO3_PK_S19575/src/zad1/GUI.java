/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Currency;


public class GUI extends Application {
    Service my_service;
    String cityStr;
    TabPane tabPane = new TabPane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Puchko Konstantsin S19575");

        ComboBox<String> stringComboBox = new ComboBox<>();
        stringComboBox.getItems().addAll(Service.map.keySet());
        stringComboBox.setOnAction(event -> {
            if (stringComboBox.getValue() != null) {
                my_service = new Service(stringComboBox.getValue());
            }
        });

        TextField city = new TextField();
        city.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.cityStr = city.getText();
                try {
                    createTabs();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        createTabs();

        primaryStage.setScene(new Scene(new VBox(new VBox(new HBox(new Label("Select country: "), stringComboBox),
                new HBox(new Label("Select city: "), city)), tabPane)));
        primaryStage.setMaximized(true);
        primaryStage.show();

    }

    private void createTabs() throws IOException {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(Service.set);

        VBox currency = new VBox(new Label("Select currency: "), comboBox,
                new Label(my_service !=null ? my_service.getNBPRate() + Currency.getInstance(my_service.country).getCurrencyCode() : ""));

        comboBox.setOnAction(event -> {
            if (comboBox.getValue() == null) {
                return;
            }
            VBox currencyWithRate = null;
            try {
                currencyWithRate = new VBox(new Label("Select currency: "), comboBox,
                        new Label(getRates(comboBox.getValue())),
                                new Label(my_service.getNBPRate() + Currency.getInstance(my_service.country).getCurrencyCode()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            tabPane.getTabs().remove(1);
            tabPane.getTabs().add(1, new Tab("Valuta", currencyWithRate));
            tabPane.getSelectionModel().select(1);
        });

        Tab tab = new Tab("Wiki", new Label("Enter the country and city"));
        if (my_service != null) {
            WebView wiki = new WebView();
            wiki.getEngine().load("https://en.wikipedia.org/wiki/" + cityStr);
            tab = new Tab("Wiki", wiki);
        }

        tabPane.getTabs().clear();
        tabPane.getTabs().addAll(new Tab("Weather", new Label(cityStr ==null?"Enter the country and city":getWeather(cityStr))), new Tab("Valuta", currency), tab);
    }

    private String getRates(String currency) throws IOException {
        String request = "https://api.exchangerate.host/latest?base=" + Currency.getInstance(my_service.country).getCurrencyCode() + "&symbols=" + currency;
        JSONObject currencyOb = new JSONObject(Service.getUrlConnection(request));
        return Currency.getInstance(my_service.country).getCurrencyCode() + " to " + currency + " = " + currencyOb.getJSONObject("rates").getDouble(currency);
    }

    private String getWeather(String city) throws IOException {
        return my_service.getWeather(city);
    }

    public static void launch(String[] args){
        Application.launch(args);
    }
}
