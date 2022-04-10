package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import serverclientstuff.User;

import java.io.IOException;

public class AccountDeletionController {

    Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() { return this.client; }

    private String userExists;
    private boolean userPassCorrect;

    @FXML
    TextField userField;
    @FXML
    PasswordField passField;
    @FXML
    PasswordField passConfirmField;
    @FXML
    Label errLabel;
    @FXML
    Button deleteAccountButton;
    @FXML
    Button closePopUpButton;

    @FXML
    //Deletes Account
    private void deleteAccountButtonAction() throws IOException {
        if(!(passField.getText()).equals(passConfirmField.getText())){
            errLabel.setText("The passwords do not match!");

        }
        else if(userField.getText().trim().isEmpty()){
            errLabel.setText("You have not entered a username!");
        }

        else if(passField.getText().trim().isEmpty()){
            errLabel.setText("You have not entered a password!");
        }
        else {
            User currUser = new User(userField.getText(), passField.getText());
            //client.verifyUser(currUser); //TODO: Verification always fails??
            client.deleteUser(currUser);
        }
    }

}
