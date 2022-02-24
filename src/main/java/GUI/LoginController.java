package GUI;


import GUI.LoginApplication;
import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import server.ServerUserHandler;
import serverclientstuff.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginController {

    //Stores the client object that lets the GUI communicate with the server
    protected Client client; // Declare empty client
    protected User currUser;


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
    public void initialize() throws IOException {
        client = new Client(); // Creates new instance of client object
        client.startConnection("127.0.0.1", 5555); // Pressing "Hello" button starts the client



    }




    @FXML
    //Closes the application
    private void exitButtonAction() throws IOException {
        //Doesn't try to close a connection that isn't there
        if(client.isConnected()) {
            client.closeConnection(); // Pressing "Exit" button closes the client
        }
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }


    //TODO - MAKE IT SO YOU CANT HAVE SPACES IN ANY OF THE FIELDS


    @FXML
    //Tries to login using the data provided
    //For now creates a user but that should all be handled on the client not the GUI :)
    //Bypasses all the networking stuff while I wait for integration - JI
    //Shouldn't have to throw the exception because we only want to make the user and transfer that to the server

    private void loginButtonAction() throws NoSuchAlgorithmException, IOException {

        if(userTextField.getText().trim().isEmpty()) {

            errorLabel.setText("You have not entered a username!");

        }

        else if(userPassField.getText().trim().isEmpty()){

            errorLabel.setText("You have not entered a password!");
        }

        else if(!client.isConnected()){
            errorLabel.setText("Cannot connect to server!");
        }


        else {

            errorLabel.setText("");

            System.out.println("Username: " + userTextField.getText());
            System.out.println("Password: " + userPassField.getText());

           currUser = new User(userTextField.getText(), userPassField.getText());
           currUser.hashUserInfo();


           String verifyCode = client.verifyUser(currUser);



           if (verifyCode.equals("USERNOTFOUND")) {
                errorLabel.setText("User does not exist!");
            }
            else if (verifyCode.equals("BADPASS")) {
                errorLabel.setText("Incorrect Password!");
            } else {
                errorLabel.setText("");

               Stage currStage = (Stage) exitButton.getScene().getWindow();
               currStage.close();


                //Opens the main application once you have logged in
                MainApplication app = new MainApplication();
                Stage mainStage = new Stage();
                app.transferInfo(mainStage, client, currUser);





            }
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