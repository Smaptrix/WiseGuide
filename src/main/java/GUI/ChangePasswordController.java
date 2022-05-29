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
 * <p>
 *     Controls the page which the user can use to change their password
 * </p>
 */
public class ChangePasswordController {

    /**
     * <p>
     *     The current client
     * </p>
     */
    Client client;

    /**
     * <p>
     *     The current user
     * </p>
     */
    User currUser;

    /**
     * <p>
     *     The button to signify the user has completed changing their password
     * </p>
     */
    @FXML
    Button changePassButton;
    /**
     * <p>
     *     The label that displays the error information
     * </p>
     */
    @FXML
    Label errLabel;

    /**
     * <p>
     *     The title
     * </p>
     */
    @FXML
    Label title;

    /**
     * <p>
     *     The current password label
     * </p>
     */
    @FXML
    Label currPassLabel;

    /**
     * <p>
     *     The new password label
     * </p>
     */
    @FXML
    Label newPassLabel;

    /**
     * <p>
     *     The new password confirmation label
     * </p>
     */
    @FXML
    Label confirmLabel;

    /**
     * <p>
     *     Sets the client
     * </p>
     * @param client the client we want to set for the controller
     */
    public void setClient(Client client){
        this.client = client;
    }

    /**
     *<p>
     *      Sets the current user
     *</p>
     * @param currUser the current user we want to set for the controller
     */
    public void setUser(User currUser){
        this.currUser = currUser;
    }

    /**
     *<p>
     *      Gets the current user
     *</p>
     * @return the current user.
     */
    public User getUser(){
        return currUser;
    }

    /**
     * <p>
     *     The password field which the user should type there current password into
     * </p>
     */
    @FXML
    PasswordField currPasswordField;
    /**
     * <p>
     *     The password field which the user should type there new password into
     * </p>
     */
    @FXML
    PasswordField newPasswordField;
    /**
     * <p>
     *     The password field which the user should retype there new password into
     * </p>
     */
    @FXML
    PasswordField newPasswordConfirmField;


    /**
     * <p>
     *     Tells the client to contact the server nad request a password change
     * </p>
     */
    @FXML
    public void onChangePassButtonPress(){

        String currPass = currPasswordField.getText();

        String desiredPass = newPasswordField.getText();
        String desiredPassConfirm = newPasswordConfirmField.getText();


        //Check the users passwords match
        if(!desiredPass.equals(desiredPassConfirm)){
            errLabel.setText("Your new passwords don't match!");
        } else if(currPass.isBlank() || desiredPass.isBlank() || desiredPassConfirm.isBlank() ) {
            errLabel.setText("You must fill in all the fields!");
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
