package org.atm.atminterface.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;

@Data
public class StartController {
    @FXML
    public BorderPane start_bdr;
    @FXML
    public Button loginBtn;
    @FXML
    public Button singBtn;

    private Stage stage;


    @FXML
    void loginPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/atm/atminterface/login.fxml"));
        Parent loginPane = loader.load();

        Stage stage = (Stage) start_bdr.getScene().getWindow();

        Scene scene = new Scene(loginPane);
        stage.setTitle("Login");
        stage.setScene(scene);

        LoginController loginController = loader.getController();
        loginController.setStage(stage);

    }

    @FXML
    void singUpPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/atm/atminterface/signUp.fxml"));
        Parent signupPane = loader.load();

        Stage stage = (Stage) start_bdr.getScene().getWindow();

        Scene scene = new Scene(signupPane);
        stage.setTitle("Sign Up");
        stage.setScene(scene);

        SignUpController signUpController = loader.getController();
        signUpController.setStage(stage);
    }
}
