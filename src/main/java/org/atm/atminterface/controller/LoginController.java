package org.atm.atminterface.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;
import org.atm.atminterface.dao.CustomerRepo;
import org.atm.atminterface.model.Customer;

import java.io.IOException;
import java.sql.SQLException;

@Data
public class LoginController {
    @FXML
    public BorderPane login_bdr;
    @FXML
    public Button backToSingUp;
    @FXML
    public Text login_sts;
    @FXML
    private TextField account_no;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField pin;

    private Stage stage;

    private Customer customer;

    private CustomerRepo repo;
    public void initialize(){
        repo = new CustomerRepo();
    }

    public void backToSignUp(ActionEvent actionEvent) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/org/atm/atminterface/signUp.fxml"));
        Parent loginPane = loader.load();

        Stage stage = (Stage) login_bdr.getScene().getWindow();

        Scene scene = new Scene(loginPane);
        stage.setTitle("Sign Up");
        stage.setScene(scene);

        SignUpController sign = loader.getController();
        sign.setStage(stage);

    }

    @FXML
    private void goToHome(ActionEvent actionEvent) throws IOException {
        login_sts.setVisible(true);

        String accountNumber = account_no.getText();
        String enteredPin = pin.getText();

        if (accountNumber.isEmpty() || enteredPin.isEmpty()) {
            login_sts.setText("Please enter account number and PIN");
            login_sts.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            customer = repo.getCustomerByAccountNumber(accountNumber);

            if (customer != null) {
                String storedPin = customer.getPin();
                if (storedPin != null && storedPin.equals(enteredPin)) {
                    login_sts.setText("Account Found! Logging in...");
                    login_sts.setStyle("-fx-text-fill: green;");


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/atm/atminterface/home.fxml"));
                    Parent homePane = loader.load();
                    Stage stage = (Stage) login_bdr.getScene().getWindow();
                    Scene scene = new Scene(homePane);
                    stage.setTitle("Home");
                    stage.setScene(scene);

                    HomeController homeController = loader.getController();
                    homeController.setStage(stage);

                    homeController.setCustomer(customer);
                } else {
                    login_sts.setText("Wrong PIN! Please try again.");
                    login_sts.setStyle("-fx-text-fill: red;");
                }
            } else {
                login_sts.setText("Account Not Found!");
                login_sts.setStyle("-fx-text-fill: red;");
            }
        } catch (SQLException e) {
            login_sts.setText("Error accessing database.");
            login_sts.setStyle("-fx-text-fill: red;");
        }
    }


}
