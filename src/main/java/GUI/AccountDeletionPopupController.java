package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountDeletionPopupController {

    private Client client;
    public void setClient(Client client){ this.client = client; };


    @FXML
    Label infoLabel1;

    @FXML
    Label infoLabel0;

    @FXML
    Label infoLabel2;

    @FXML
    Button closePopUpButton;

    public void closePopUpButton() throws IOException {

        if(client == null){
            System.out.println("The client is null");
        }

        //Close popup window
        Stage stage = (Stage) closePopUpButton.getScene().getWindow();
        stage.close();
        reopenLoginPage();

    }

    public void setMessage(boolean error){
        if(error){
            infoLabel0.setText("An error occurred.");
            infoLabel1.setText("The account could not be deleted.");
            infoLabel2.setText("DELETEFAILURE");
        }
    }

    public void reopenLoginPage() throws IOException {
        System.out.println("Attempting to log out the client.");
        client.requestLogout();
        System.out.println("Opening the login page...");

    }

}
