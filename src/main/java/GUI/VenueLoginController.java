/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   09/03/2022
    Last Updated:   12/05/2022
 */
package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serverclientstuff.User;

import java.io.IOException;

/**
 * This Controller controls the venue login page
 */
public class VenueLoginController {

    /**
     * The client that the GUI uses to communicate with the server
     */
    Client client;

    /**
     * The current user that is logged in (Wont be active before logging in)
     */
    User currUser;

    /**
     * This text field is where the venue types in their venue name
     */
    @FXML
    TextField venueNameField;

    /**
     * This password field is where the venue types in their password
     */
    @FXML
    PasswordField venuePassField;

    /**
     * This is the button that the venue user can press to login
     */
    @FXML
    Button loginButton;
    /**
     * This is the button that the user can press to go back to the main page of the application
     */
    @FXML
    Button backButton;

    /**
     * This label displays any sort of error message that might pop up
     */
    @FXML
    Label errLabel;


    /**
     * This sets the client to be used by the GUI
     * @param client the client that we want the GUI to use
     */
    public void setClient(Client client){
        this.client = client;
    }


    /**
     * The action that occurs when the login button is pressed
     * This attempts to log the venue user into the server
     * @throws IOException if the client cannot connect to the server
     */
    @FXML
    private void onLoginButtonPress() throws IOException {

        //Make sure teh fields arent empty
        if(venueNameField.getText().isEmpty() || venuePassField.getText().isEmpty()){
            errLabel.setText("Please enter something in both fields!");
        }
        else{

            currUser = new User(venueNameField.getText(), venuePassField.getText());


            //Checks to see if the login data was correct
            if(!(client.requestVenueLogin(currUser.getUsername(), currUser.getPassword()).equals("GOODLOGIN"))){
                errLabel.setText("Unrecognised Venue Details");
            }
            //If not a bad login, it has to be a good login
            else{
                //If the login data is correct
                errLabel.setText("");
                System.out.println("Good login!");

                //Opens the main venue owner page
                FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("venue-owner-main-page.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 450, 470);


                VenueOwnerMainPageController controller = fxmlLoader.getController();
                controller.setClient(client);
                controller.setCurrUser(currUser);
                controller.populateFileList();
                stage.setScene(scene);
                stage.setTitle(currUser.getUsername() + " Owner Page");
                stage.show();
                stage.setResizable(false);
                stage.initStyle(StageStyle.UNDECORATED);


                Stage currStage = (Stage) backButton.getScene().getWindow();
                currStage.close();
            }

        }



    }


    /**
     * The action that occurs when the back button is pressed
     * This sends the user back to the main application page
     * @throws IOException If the server cannot connect to the client
     */
    @FXML
    private void onBackButtonPress() throws IOException {

        //Reopens the login page
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-page.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);

        System.out.println("Loaded login page again");

        LoginController controller = fxmlLoader.getController();
        controller.setClient(client);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
        stage.setResizable(false);


        Stage currStage = (Stage) backButton.getScene().getWindow();
        currStage.close();

    }


}
