package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import serverclientstuff.User;

public class ChangePasswordController {


    Client client;

    User currUser;


    @FXML
    Button changePassButton;

    @FXML
    Label errLabel;


    public void setClient(Client client){
        this.client = client;
    }

    public void setUser(User currUser){
        this.currUser = currUser;
    }



    @FXML
    PasswordField currPasswordField;

    @FXML
    PasswordField newPasswordField;

    @FXML
    PasswordField newPasswordConfirmField;


    @FXML
    public void onChangePassButtonPress(){

        String currPass = currPasswordField.getText();

        String desiredPass = newPasswordField.getText();
        String desiredPassConfirm = newPasswordConfirmField.getText();


        if(!desiredPass.equals(desiredPassConfirm)){
            errLabel.setText("Your new passwords dont match!");
        }


    }





}
