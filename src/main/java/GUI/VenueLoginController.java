package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class VenueLoginController {

    Client client;


    @FXML
    TextField venueName;

    @FXML
    PasswordField venuePass;

    @FXML
    Button loginButton;



    public void setClient(Client client){
        this.client = client;
    }




    @FXML
    public void onLoginButtonPress(){

        System.out.println("Test");

    }




}
