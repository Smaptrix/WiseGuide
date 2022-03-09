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
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.IOException;



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
    MenuItem menuClose;
    @FXML
    Label errorLabel;



    @FXML
    //Always called by the FXML Loader
    public void initialize() {

    }


    public void initialConnection() throws IOException {

        client = new Client(); // Creates new instance of client object
        client.startConnection("127.0.0.1", 5555);

    }




    @FXML
    //Closes the application
    private void exitButtonAction() throws IOException {
        //Doesn't try to close a connection that isn't there
        if(client.isConnected()) {
            client.closeConnection(); // Closes Client connection safely
        }
        System.exit(0);
    }


    //TODO - MAKE IT SO YOU CANT HAVE SPACES IN ANY OF THE FIELDS


    @FXML
    //Tries to login using the data provided
    //For now creates a user but that should all be handled on the client not the GUI :)
    //Bypasses all the networking stuff while I wait for integration - JI
    //Shouldn't have to throw the exception because we only want to make the user and transfer that to the server

    private void loginButtonAction() throws IOException {

        if(userTextField.getText().trim().isEmpty()) {

            errorLabel.setText("You have not entered a username!");

        }

        else if(userPassField.getText().trim().isEmpty()){

            errorLabel.setText("You have not entered a password!");
        }

        else if(!client.isConnected()){
            errorLabel.setText("Cannot connect to server!");
        }

        else if(!client.isSameVersion()){
            errorLabel.setText("Server and Client are different Versions!");
        }


        else {

            errorLabel.setText("");



           currUser = new User(userTextField.getText(), userPassField.getText());
           currUser.hashUserInfo();


           String loginCode = client.requestLogin(currUser);


           if(loginCode.equals("BADLOGIN")){
               errorLabel.setText("Unrecognised user details");

            }

           //If not BADLOGIN assume GOODLOGIN
           else {
                errorLabel.setText("");



               Stage currStage = (Stage) loginButton.getScene().getWindow();
               currStage.close();


               //Opens the main application once you have logged in
               MainApplication app = new MainApplication();
               Stage mainStage = new Stage();
               app.transferInfoAndOpen(mainStage, client, currUser);






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
            AccountCreationController controller = fxmlLoader.getController();
            controller.setClient(client);
            stage.setScene(scene);
            stage.setTitle("Account Creation");
            stage.show();

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void setClient(Client client) {
        this.client = client;
    }
}