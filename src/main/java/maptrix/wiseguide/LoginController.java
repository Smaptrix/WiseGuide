package maptrix.wiseguide;


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

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginController {


    //Imports all of the objects in the login 'scene'
    @FXML
    PasswordField userPassField;
    @FXML
    TextField userTextField;
    @FXML
    Button createAccButton;
    @FXML
    Button loginButton;
    @FXML
    Button exitButton;

    @FXML
    Label errorLabel;



    @FXML
    //Always called by the FXML Loader
    public void initialize(){



    }




    @FXML
    //Closes the application
    private void exitButtonAction(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    //TODO - Post Integration let the client handle the user stuff not the GUI

    @FXML
    //Tries to login using the data provided
    //For now creates a user but that should all be handled on the client not the GUI :)
    //Bypasses all the networking stuff while I wait for integration - JI
    //Shouldn't have to throw the exception because we only want to make the user and transfer that to the server

    private void loginButtonAction() throws NoSuchAlgorithmException, IOException {

        System.out.println("Username: " + userTextField.getText());
        System.out.println("Password: " + userPassField.getText());

        ServerUserHandler currUser = new ServerUserHandler(new User(userTextField.getText(), userPassField.getText()));

        System.out.println("User Exist State: " + currUser.userExistState);

        if(!currUser.userExistState){
            errorLabel.setText("User does not exist!");
        }
        if(!currUser.passVerified){
            errorLabel.setText("Incorrect Password!");
        }
        else{
            errorLabel.setText("");
        }

    }

    @FXML
    //Opens the Account Creation Page
    private void createAccButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-create-page.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 300, 350);
            stage.setScene(scene);
            stage.setTitle("Account Creation");
            stage.show();

        }catch(IOException e){
            e.printStackTrace();
        }

    }






}