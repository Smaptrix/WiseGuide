package maptrix.wiseguide;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.ServerUser;
import serverclientstuff.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginController {
    @FXML
    private Label welcomeText;

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
        System.out.println("closing down!");
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    //Tries to login using the data provided
    //For now creates a user but that should all be handled on the client not the GUI :)
    //Bypasses all the networking stuff while I wait for integration - JI
    //Shouldn't have to throw the exception because we only want to make the user and transfer that to the server

    private void loginButtonAction() throws NoSuchAlgorithmException, IOException {

        System.out.println("Username: " + userTextField.getText());
        System.out.println("Password: " + userPassField.getText());

        ServerUser currUser = new ServerUser(new User(userTextField.getText(), userPassField.getText()));

        System.out.println("User Exist State: " + currUser.userExistState);




    }

}