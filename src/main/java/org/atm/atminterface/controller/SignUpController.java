package org.atm.atminterface.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;
import org.atm.atminterface.dao.CustomerRepo;
import org.atm.atminterface.model.Customer;
import org.atm.atminterface.service.AccountNumberGenerator;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Data
public class SignUpController {
    @FXML
    public BorderPane signup_bdr;
    @FXML
    public Button backToLogin;
    @FXML
    public Text pin_sts;
    @FXML
    private PasswordField cPin;

    @FXML
    private DatePicker dob;

    @FXML
    private TextField fName;

    @FXML
    private TextField lName;

    @FXML
    private TextField phone;

    @FXML
    private PasswordField rPin;

    @FXML
    private Button signBtn;

    @FXML
    private Text signUp_sts;

    private Stage stage;

    protected static CustomerRepo repo;

    private static Customer customer;

    public void initialize() {
        repo = new CustomerRepo();

        signBtn.setDisable(true);
        fName.textProperty().addListener((_, _, _) -> checkFields());
        lName.textProperty().addListener((_, _, _) -> checkFields());
        phone.textProperty().addListener((_, _, _) -> checkFields());
        cPin.textProperty().addListener((_, _, _) -> checkFields());
        rPin.textProperty().addListener((_, _, _) -> checkFields());
        dob.valueProperty().addListener((_, _, _) -> checkFields());
    }
    private void checkFields() {
        pin_sts.setVisible(true);
        boolean allFieldsFilled = !fName.getText().isEmpty()
                && !lName.getText().isEmpty()
                && !phone.getText().isEmpty() && phone.getText().length()==10
                && dob.getValue() != null
                && !cPin.getText().isEmpty() && cPin.getText().length()==3
                && !rPin.getText().isEmpty() && rPin.getText().length()==3;


        LocalDate date = LocalDate.now();
        int diff = date.getYear() - dob.getValue().getYear();


        if (allFieldsFilled) {
            pin_sts.setStyle("-fx-text-fill: green;");
            pin_sts.setText("Great");
            enableSignUpButton(true);
        } else {
            pin_sts.setStyle("-fx-text-fill: red;");
            if(diff<18){
                pin_sts.setText("Your Not Eligible For creating ATM Account ");
                enableSignUpButton(false);
                return;
            }

             if(phone.getText().length()!=10) {
                 pin_sts.setText("Phone number should be of 10 digit");
                 enableSignUpButton(false);
                 return;
             }
             if(cPin.getText().length()!=3 || rPin.getText().length()!=3) {
                 pin_sts.setText("Pin Length should be 3 digit");
                 enableSignUpButton(false);
             }
             else{
                 pin_sts.setText("Some Fields Missing");
                 enableSignUpButton(false);
             }

        }
    }

    private void enableSignUpButton(boolean enable) {
        signBtn.setDisable(!enable);
    }
    @FXML
    public void backToSignUp(ActionEvent actionEvent) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/org/atm/atminterface/login.fxml"));
        Parent loginPane = loader.load();

        Stage stage = (Stage) signup_bdr.getScene().getWindow();

        Scene scene = new Scene(loginPane);
        stage.setTitle("Login");
        stage.setScene(scene);

        LoginController login = loader.getController();
        login.setStage(stage);

    }

    @FXML
    public void goToHome(ActionEvent event) throws IOException, SQLException {
        LocalDate date = dob.getValue();
        String formattedDate = null;
        if (date != null) {
            formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        }
        boolean pin = checkPinSame();

        if(pin){
            AccountNumberGenerator generator = new AccountNumberGenerator();
            String account_no=generator.generateAccountNumber();
            customer = new Customer();
            customer.setAmount(0.0);
            customer.setDob(formattedDate);
            customer.setPhone(phone.getText());
            customer.setFName(fName.getText());
            customer.setLName(lName.getText());
            customer.setPin(cPin.getText());
            customer.setAccount_no(account_no);
            boolean res = repo.save(customer);
            if(!res){
                signUp_sts.setVisible(true);
                signUp_sts.setText("Phone Number already exist");
                return;
            }
            showAccountNumber(account_no);
        }
    }
    public void showAccountNumber(String account_no){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account Created");
        alert.setHeaderText("Account Created Successfully");
        alert.setContentText("Your account number is: " + account_no);
        alert.showAndWait().ifPresent(response -> {
            try{
                transitionToHome();
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        });

    }
    private void transitionToHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/atm/atminterface/home.fxml"));
        Parent homePane;
        homePane = loader.load();
        Stage stage = (Stage) signup_bdr.getScene().getWindow();
        Scene scene = new Scene(homePane);
        stage.setTitle("Home");
        stage.setScene(scene);

        HomeController homeController = loader.getController();
        homeController.setStage(stage);
        homeController.setCustomer(customer);
    }
    public boolean checkPinSame(){
        boolean status = false;
        pin_sts.setVisible(true);
        if(cPin.getText().equals(rPin.getText())){
            pin_sts.setText("Matched Pin");
            pin_sts.setStyle("-fx-text-fill: green;");
            status = true;
        }else {
            pin_sts.setText("Miss Matched Pin");
            pin_sts.setStyle("-fx-text-fill: red;");
        }
        return status;
    }

}