package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class VenueLoginController {

    Client client;


    @FXML
    TextField venueName;

    @FXML
    PasswordField venuePass;

    @FXML
    Button loginButton;

    @FXML
    Button backButton;


    public void setClient(Client client){
        this.client = client;
    }




    @FXML
    private void onLoginButtonPress() throws IOException {

        client.requestVenueLogin(venueName.getText(), venuePass.getText());

    }
    //TODO - IDEAS
    //     - FOR THE VENUE PRODUCE A LIST OF ALL FILES CURRENTLY STORED IN VENUES DIRECTORY
    //     - LET THE VENUE ADD OR DELETE MORE FILES?!?!



    @FXML
    private void onBackButtonPress() throws IOException {

        //Reopens the login page
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-page.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);

        System.out.println("Loaded login page again");

        LoginController controller = fxmlLoader.getController();
        controller.setClient(client);
        stage.setScene(scene);
        stage.setTitle("Welcome to WiseGuide");
        stage.show();


        Stage currStage = (Stage) backButton.getScene().getWindow();
        currStage.close();

    }


}
