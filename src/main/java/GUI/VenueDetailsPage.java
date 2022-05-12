/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   09/03/2022
    Last Updated:   12/05/2022
 */

package GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * <p>
 *     This is the page that displays the venue details (including all of the media)
 * </p>
 */
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

    /**
     *<p>
     *     The action that occurs when the exit button is pressed
     *      Exits the application
     *</p>
     */
    @FXML
    private void exitButtonAction(){
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();

    }
}
