/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   18/02/2022
    Last Updated:   02/06/2022
 */

package GUI;


import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ServerClientUtility.User;
import java.io.IOException;

/**
 * <p>
 *     This controls the login page of the GUI
 * </p>
 */
public class LoginController {

    /**
     * <p>
     *     The client being used by the GUI
     * </p>
     */
    public Client client;
    /**
     * <p>
     *     The user being used by the controller - not filled until logged in
     * </p>
     */
    protected User currUser;

    private boolean testingMode = false;
    public void setTestingMode(boolean testingMode) {
        this.testingMode = testingMode;
    }

    /**
     * <p>
     *     The text field where the user can enter their password
     * </p>
     */
    @FXML
    PasswordField userPassField;
    /**
     * <p>
     *     The text field where the user can enter their username
     * </p>
     */
    @FXML
    TextField userTextField;
    /**
     * <p>
     *     The button a user can press if they wish to create an account
     * </p>
     */
    @FXML
    Button createAccButton;
    /**
     * <p>
     *     The button a user presses if they wish to log in
     * </p>
     */
    @FXML
    Button loginButton;

    /**
     * <p>
     *     The close button on the menu
     * </p>
     */
    @FXML
    MenuItem menuClose;
    /**
     * <p>
     *     The label which displays error information
     * </p>
     */
    @FXML
    Label errorLabel;
    /**
     * <p>
     * The label next to the username field
     * </p>
     */
    @FXML
    Label usernameLabel;
    /**
     * <p>
     * The label next to the password field
     * </p>
     */
    @FXML
    Label passwordLabel;
    /**
     * <p>
     * The image containing the maptrix logo
     * </p>
     */
    @FXML
    ImageView maptrixLogo;
    /**
     * <p>
     *     The button on the menubar which lets you see your details
     * </p>
     */
    @FXML
    MenuItem accountDetailsButton;


    /**
     * <p>
     *     This function runs at the start of the page opening
     * </p>
     */
    @FXML
    //Always called by the FXML Loader
    public void initialize() {

    }

    /**
     * <p>
     *     Creates the client object to connect to the server
     * </p>
     * @throws IOException If the client can't connect to the server
     */
    public void initialConnection() throws IOException {

        client = new Client(); // Creates new instance of client object

        String defaultIP = "127.0.0.1";
        int defaultPort = 5555;


        client.startConnection(defaultIP, defaultPort);
        System.out.println("Connection Started!");
    }

    /**
     * <p>
     *     CLoses the application when the exit button is pressed
     * </p>
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



    /**
     * <p>
     *     When the user presses the button- attempts to log the client into the server
     * </p>
     * @throws IOException If the client cannot connect to the server
     */
    @FXML
    private void loginButtonAction() throws IOException {

        //If the username text field is empty
        if (userTextField.getText().trim().isEmpty()) {

            errorLabel.setText("You have not entered a username!");

        }
        //If the password field is empty
        else if (userPassField.getText().trim().isEmpty()) {

            errorLabel.setText("You have not entered a password!");
        }
        //If the client isn't connected to the server
        else if (!client.isConnected()) {
            errorLabel.setText("Cannot connect to server!");
        }
        //If the server and the client have seperate versions
        else if (!client.isSameVersion()) {
            errorLabel.setText("Server and Client are different Versions!");
        } else {

            errorLabel.setText("");
            currUser = new User(userTextField.getText(), userPassField.getText());

            String loginCode = client.requestLogin(currUser);

            //If the message recieved is anything other than GOODLOGIN don't login
            if (!(loginCode.equals("GOODLOGIN"))) {
                errorLabel.setText("Unrecognised user details");
            }
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
     * <p>
     *     Opens the create account page when the button is pressed
     * </p>
     */
    @FXML
    private void createAccButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-create-page.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 300, 350);
            AccountCreationController controller = fxmlLoader.getController();
            controller.setClient(client);
            controller.setTestingMode(testingMode);
            stage.setScene(scene);
            stage.setTitle("Account Creation");
            stage.show();
            stage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //FOR TESTING PURPOSES: CREATE AN ACCOUNT TO DELETE
    public void createTestAccount() throws IOException {
        User testingUser = new User("accountTestUser","accountTest");
        if(client.createUser(testingUser).equals("USERCREATED")) {
            System.out.println("ACCOUNT TEST USER was created.");
        } else {
            System.out.println("ACCOUNT TEST USER could not be created.");
        }
    }

    /**
     * <p>
     *     Sets the client to be used with the controller
     * </p>
     * @param client The client to give to the controller
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Gets the current client being used by the login application
     * @return the current client
     */
    public Client getClient() { return this.client; }

    /**
     * <p>
     *     Opens the venue login page when the "secret" button is pressed
     * </p>
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

    /**
     * Gets the current user from the login controlle
     * Isn't necessary because noone can be logged in yet
     * @return the current user
     */
    public User getCurrUser() {
        return currUser;
    }
}