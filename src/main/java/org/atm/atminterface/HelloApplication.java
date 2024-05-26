package org.atm.atminterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.atm.atminterface.controller.StartController;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/atm/atminterface/start.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("ATM Interface");
        stage.show();

        StartController startController = loader.getController();
        startController.setStage(stage);

    }

    public static void main(String[] args) {
        launch();
    }
}