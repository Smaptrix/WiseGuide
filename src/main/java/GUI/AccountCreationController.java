/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   18/02/2022
    Last Updated:   24/02/2022
 */
package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.ServerUserHandler;
import serverclientstuff.User;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class AccountCreationController {


    Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    TextField userField;

    @FXML
    PasswordField passField;

    @FXML
    PasswordField passConfirmField;

    @FXML
    Label errLabel;

    @FXML
    Button createAccountButton;

    @FXML
    Button closePopUpButton;

    @FXML
    CheckBox ageCheckBox;


    @FXML
    Hyperlink privacyPolicyLink;

    @FXML
    Hyperlink termsLink;




    //TODO - Post Integration let the client handle all the user stuff not the GUI


    @FXML
    //Attempts to create account
    //For now bypasses networking stuff while it waits for GUI and client/server integration - JI
    //Should not have to deal with the user exceptions thrown here, just the creation of the user object
    //Only here for now because we are bypassing network stuff
    private void createAccountButtonAction() throws NoSuchAlgorithmException, IOException {
        System.out.println("Wanted Username: " + userField.getText());

        System.out.println("Password: " + passField.getText());

        System.out.println("Confirm Password: " + passConfirmField.getText());

        //Big check to make sure username and password stuff is correct

        //If the password and the confrimed password dont match
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

        else{
            errLabel.setText("");

            User newUser = new User(userField.getText(), passField.getText());
            ServerUserHandler desiredUser = new ServerUserHandler(newUser);

            if(desiredUser.userExistState){
                errLabel.setText("This username is taken");
            }
            else{

                if(client.createUser(newUser).equals("USERCREATED")){

                    FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-created-window.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 280, 155);
                    stage.setScene(scene);
                    stage.setTitle("Account Created");
                    stage.show();


                    //Close the current page
                    Stage currStage = (Stage) createAccountButton.getScene().getWindow();
                    currStage.close();

                }
                //TODO - Account creation failed page
            }
        }



    }


    @FXML
    //Closes popup window
    private void closePopupButton(){
        Stage stage = (Stage) closePopUpButton.getScene().getWindow();
        stage.close();
    }


    //Both methods below are used to link to company documents that the user is advised to read
    @FXML
    private void privacyPolicyLinkAction() throws IOException, URISyntaxException {

        Desktop.getDesktop().browse(new URL("https://docs.google.com/document/d/1ALaRldIS1v9--ShyWjUDaXeeTgeiC5f-x4AlE0A3PxQ/edit?usp=sharing").toURI());

    }

    @FXML
    private void termsLinkAction() throws IOException, URISyntaxException {

        Desktop.getDesktop().browse(new URL("https://docs.google.com/document/d/1mkBgUcoBHEGQWlDHR5cuLER7AewNDEgQsZZBejyExT0/edit?usp=sharing").toURI());

    }





}
