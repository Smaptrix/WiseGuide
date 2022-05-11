/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   18/02/2022
    Last Updated:   11/05/2022
 */

package GUI;


import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serverclientstuff.User;

import java.io.IOException;


/**
 * Controls the login page for the application
 */
public class LoginController {


    /**
     * The client being used by the GUI
     */
    protected Client client;
    /**
     * The user being used by the controller - not filled until logged in
     */
    protected User currUser;


    /**
     * The text field where the user can enter their password
     */
    @FXML
    PasswordField userPassField;
    /**
     * The text field where the user can enter their username
     */
    @FXML
    TextField userTextField;
    /**
     * The button a user can press if they wish to create an account
     */
    @FXML
    Button createAccButton;
    /**
     * The button a user presses if they wish to log in
     */
    @FXML
    Button loginButton;

    /**
     * The close button on the menu
     */
    @FXML
    MenuItem menuClose;
    /**
     * The label which displays error information
     */
    @FXML
    Label errorLabel;
    /**
     * The image containing the maptrix logo
     */
    @FXML
    ImageView maptrixLogo;
    /**
     * The button on the menubar which lets you see your details
     */
    @FXML
    MenuItem accountDetailsButton;


    /**
     * This function runs at the start of the page opening
     */
    @FXML
    //Always called by the FXML Loader
    public void initialize() {

    }

    /**
     * Creates the client object to connect to the server
     * @throws IOException If the client can't connect to the server
     */
    public void initialConnection() throws IOException {

        client = new Client(); // Creates new instance of client object
        client.startConnection("127.0.0.1", 5555);

    }

    /**
     * CLoses the application when the exit button is pressed
     * @throws IOException If the client cannot connect to the server
     */
    @FXML
    private void exitButtonAction() throws IOException {
        //Doesn't try to close a connection that isn't there
        if (client.isConnected()) {
            client.closeConnection(); // Closes Client connection safely
        }
        System.exit(0);
    }


    //TODO - MAKE IT SO YOU CANT HAVE SPACES IN ANY OF THE FIELDS

    /**
     * When the user presses the button- attempts to log the client into the server
     * @throws IOException If the client cannot connect to the server
     */
    @FXML
    private void loginButtonAction() throws IOException {

        if (userTextField.getText().trim().isEmpty()) {

            errorLabel.setText("You have not entered a username!");

        } else if (userPassField.getText().trim().isEmpty()) {

            errorLabel.setText("You have not entered a password!");
        } else if (!client.isConnected()) {
            errorLabel.setText("Cannot connect to server!");
        } else if (!client.isSameVersion()) {
            errorLabel.setText("Server and Client are different Versions!");
        } else {

            errorLabel.setText("");


            currUser = new User(userTextField.getText(), userPassField.getText());


            String loginCode = client.requestLogin(currUser);


            if (!(loginCode.equals("GOODLOGIN"))) {
                errorLabel.setText("Unrecognised user details");
            }

            //If not BADLOGIN assume GOODLOGIN - shouldn't this be the other way around?? (JI)
            else{
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

    /**
     * Opens the create account page when the button is pressed
     */
    @FXML
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
            stage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets the client to be used with the controller
     * @param client The client to give to the controller
     */
    public void setClient(Client client) {
        this.client = client;
    }


    /**
     * Opens the venue login page when the "secret" button is pressed
     * @throws IOException If the client cannot connect to the server
     */
    @FXML
    private void venueLoginPageOpen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("venue-login-page.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        VenueLoginController controller= fxmlLoader.getController();
        controller.setClient(client);
        stage.setScene(scene);
        stage.setTitle("Venue Login");
        stage.show();
        stage.setResizable(false);

        Stage currStage = (Stage) errorLabel.getScene().getWindow();
        currStage.close();
    }

    }