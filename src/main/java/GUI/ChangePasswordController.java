/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   18/02/2022
    Last Updated:   11/05/2022
 */
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

/**
 * Controls the page which the user can use to change their password
 */
public class ChangePasswordController {

    /**
     * The current client
     */
    Client client;

    /**
     * The current user
     */
    User currUser;

    /**
     * The button to signify the user has completed changing their password
     */
    @FXML
    Button changePassButton;
    /**
     * The label that displays the error information
     */
    @FXML
    Label errLabel;

    /**
     * Sets the client
     * @param client the client we want to set for the controller
     */
    public void setClient(Client client){
        this.client = client;
    }

    /**
     * Sets the current user
     * @param currUser the current user we want to set for the controller
     */
    public void setUser(User currUser){
        this.currUser = currUser;
    }

    /**
     * The password field which the user should type there current password into
     */
    @FXML
    PasswordField currPasswordField;
    /**
     * The password field which the user should type there new password into
     */
    @FXML
    PasswordField newPasswordField;
    /**
     * The password field which the user should retype there new password into
     */
    @FXML
    PasswordField newPasswordConfirmField;


    /**
     * Tells the client to contact the server nad request a password change
     */
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



                String changePassResult = client.requestPasswordChange(currPass, desiredPass);


                if(changePassResult.equals("INCORRECTPASS")){

                    errLabel.setText("Your current password is incorrect!");
                }
                else if(changePassResult.equals("PASSCHANGED")){
                    currUser.setPassword(desiredPass);



                   Stage currScene = (Stage) changePassButton.getScene().getWindow();
                   currScene.close();
                }
                else{
                    errLabel.setText("Unknown error");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }





}
