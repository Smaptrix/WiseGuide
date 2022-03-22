package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

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
    public void onLoginButtonPress() throws IOException {

        client.requestVenueLogin(venueName.getText(), venuePass.getText());

    }




}
