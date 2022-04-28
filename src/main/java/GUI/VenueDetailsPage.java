

package GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class VenueDetailsPage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("VenueDetailsPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("WiseGuide by Maptrix - V1.0.0");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }


    @FXML
    //Closes the application
    private void exitButtonAction(){
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();

    }
}
