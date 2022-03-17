package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.IOException;

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


        //Check the users passwords match
        if(!desiredPass.equals(desiredPassConfirm)){
            errLabel.setText("Your new passwords dont match!");
        }


        else if(desiredPass.equals(currPass)){
            errLabel.setText("You cannot change your password to your current password!");
        }

        else{

            try {


                if(client.requestPasswordChange(currPass, desiredPass).equals("INCORRECTPASS")){

                    errLabel.setText("Your current password is incorrect!");
                }
                else{

                   Stage currScene = (Stage) changePassButton.getScene().getWindow();
                   currScene.close();

                }




            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }





}
