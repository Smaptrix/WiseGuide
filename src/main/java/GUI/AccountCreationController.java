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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Controls the account creator within the application
 */
public class AccountCreationController {

    /**
     * The current client
     */
    Client client;

    /**
     * Sets the client to be used by the controller
     * @param client the client you want the controller to use
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Gets the current client being used by the controller
     * @return the current client being used
     */
    public Client getClient() { return this.client; }

    /**
     * The field that the user can type their desired username into
     */
    @FXML
    TextField userField;

    /**
     * The field where the user can type their desired password into
     */
    @FXML
    PasswordField passField;

    /**
     * The confirmation field for the users desired password
     */
    @FXML
    PasswordField passConfirmField;

    /**
     * The label that displays any errors that occur
     */
    @FXML
    Label errLabel;

    /**
     * The button the users press when they want to create the account with the desired attributes
     */
    @FXML
    Button createAccountButton;

    /**
     * The button the users use to close the account creation confirmation popup
     */
    @FXML
    Button closePopUpButton;

    /**
     * The checkbox the users tick to confirm they are over the required age limit
     */
    @FXML
    CheckBox ageCheckBox;

    /**
     * The link to the companies' privacy policy
     */
    @FXML
    Hyperlink privacyPolicyLink;

    /**
     * The link to the companies terms and conditions link
     */
    @FXML
    Hyperlink termsLink;





    //TODO -  User should not be able to create accounts with same names as testing accounts (see LoginGUIIntegration TODOs) (AC)
    /**
     * When the user presses the create account button, this action occurs and requests the server to make the desired account
     * @throws IOException if the client cannot connect to the server
     */
    @FXML
    private void createAccountButtonAction() throws IOException {


        //Lots of if/elseifs to make sure that the user data stuff is correct

        //If the password and the confirmed password don't match
        if(!(passField.getText()).equals(passConfirmField.getText())){
            errLabel.setText("The passwords do not match!");

        }

        else if(userField.getText().trim().isEmpty()){
            errLabel.setText("You have not entered a username!");
        }

        else if(passField.getText().trim().isEmpty()){
            errLabel.setText("You have not entered a password!");
        }

        else if(!ageCheckBox.isSelected()){
            errLabel.setText("You are not over the age of 13!");
        }

        else if(userField.getLength() > 15){
            errLabel.setText("username can't be more than 15 characters!");
        }

        else if(passField.getLength() > 15){
            errLabel.setText("password can't be more than 15 characters!");
        }

        //If everything checks out
        else{
            errLabel.setText("");


            User currUser = new User(userField.getText(), passField.getText());

            //Requests that the server creates the user
            if(client.createUser(currUser).equals("USERCREATED")){

                    accountCreatedPageOpen();

                    //Close the current page
                    Stage currStage = (Stage) createAccountButton.getScene().getWindow();
                    currStage.close();

                }
            else {
                errLabel.setText("User already exists");
            }

            }
        }


    /**
     * Opens a popup to display that the account has been created
     * @throws IOException if the GUI cannot open the FXML file
     */
    public void accountCreatedPageOpen() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-created-window.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 280, 155);
        stage.setScene(scene);
        stage.setTitle("Account Created");
        stage.show();
    }

    /**
     * Closes the popup when pressed
     */
    @FXML
    private void closePopupButton(){
        Stage stage = (Stage) closePopUpButton.getScene().getWindow();
        stage.close();
    }


    /**
     * When the privacy policy link is pressed, this action occurs and opens the default web browser and displays the privacy policy
     * @throws IOException if the default browser cannot be opened
     * @throws URISyntaxException if the provided uri is invalid
     */
    @FXML
    private void privacyPolicyLinkAction() throws IOException, URISyntaxException {

        Desktop.getDesktop().browse(new URL("https://docs.google.com/document/d/1ALaRldIS1v9--ShyWjUDaXeeTgeiC5f-x4AlE0A3PxQ/edit?usp=sharing").toURI());

    }

    /**
     * When the privacy policy link is pressed, this action occurs and opens the default web browser and displays the terms and conditions
     * @throws IOException if the default browser cannot be opened
     * @throws URISyntaxException if the provided uri is invalid
     */
    @FXML
    private void termsLinkAction() throws IOException, URISyntaxException {

        Desktop.getDesktop().browse(new URL("https://docs.google.com/document/d/1mkBgUcoBHEGQWlDHR5cuLER7AewNDEgQsZZBejyExT0/edit?usp=sharing").toURI());

    }





}
