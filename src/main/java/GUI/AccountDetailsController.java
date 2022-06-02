package GUI;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.IOException;

/**
 * <p>
 *     Controls the page that displays the current users information
 * </p>
 */
public class AccountDetailsController {

    /**
     * <p>
     *     The current client being used by the GUI
     * </p>
     */
    Client client;

    /**
     * <p>
     *     The current user
     * </p>
     */
    User currUser;

    private Stage mapStage;

    /**
     * <p>
     *     The label that displays the current users username
     * </p>
     */
    @FXML
    Label usernameLabel;

    /**
     * <p>
     *     The label that displays the text "username"
     * </p>
     */
    @FXML
    Label usernameText;

    /**
     * <p>
     *     The label that displays the text "password"
     * </p>
     */
    @FXML
    Label passwordText;

    /**
     * <p>
     *     The button that the user can press to request a name change
     * </p>
     */
    @FXML
    Button changeNameButton;

    /**
     * <p>
     *     The button that the user can press to request a password change
     * </p>
     */
    @FXML
    Button changePassButton;

    /**
     * <p>
     *     The button that the user can press to delete their account
     * </p>
     */
    @FXML
    Button deleteAccountButton;

    /**
     * <p>
     *     This function runs at the start of the GUI element opening up
     * </p>
     */
    @FXML
    public void initialize(){

    }

    /**
     * <p>
     *     Sets the client of this controller
     * </p>
     * @param client The client
     */
    public void setClient(Client client) {
        this.client = client;

    }

    /**
     * <p>
     *     Sets the current user of this controller
     * </p>
     * @param currUser the current user
     */
    public void setUser(User currUser) {
        this.currUser = currUser;
        usernameLabel.setText(currUser.getUsername());
        System.out.println(currUser.getUsername());
    }

    /**
     * <p>
     *     Occurs when the change name button is pressed
     *     Opens the name change page
     * </p>
     * @throws IOException if the GUI cannot access the fxml file
     */
    @FXML
    protected void onchangeNameButtonPress() throws IOException {


        //Opens the fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("change-username-page.fxml"));

        //Opens the GUI page with regard to the fxml
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 375);
        ChangeUsernameController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setUser(currUser);
        stage.setScene(scene);
        stage.setTitle("Change username");
        stage.show();


        //Close the current page
        Stage currStage = (Stage) changeNameButton.getScene().getWindow();
        currStage.close();





    }

    /**
     * <p>
     *     Opens the page which the user can use to change their password
     * </p>
     * @throws IOException
     */
    @FXML
    protected void onchangePassButtonPress() throws IOException {

        //Opens the fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("change-password-page.fxml"));

        //Opens the GUI pertaining to the current fxml file
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 375);
        ChangePasswordController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setUser(currUser);
        stage.setScene(scene);
        stage.setTitle("Change Password");
        stage.show();


        //Close the current page
        Stage currStage = (Stage) changeNameButton.getScene().getWindow();
        currStage.close();

    }

    //TODO: Button on the fxml may not be centred
    @FXML
    public void onDeleteAccountButtonPress(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-delete-page.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 300, 350);
        AccountDeletionController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setCurrUser(currUser);
        controller.setParentStage((Stage)deleteAccountButton.getScene().getWindow());
        controller.setMapStage(mapStage);
        //controller.setTestingMode(testingMode); //Uncomment if a testingMode is added to main screen
        stage.setScene(scene);
        stage.setTitle("Account Deletion");
        stage.show();
        stage.setResizable(false);
    }

    public void setMapStage(Stage mapStage) {
        this.mapStage = mapStage;
    }
}
