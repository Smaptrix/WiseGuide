package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.IOException;

/**
 * Controls the page that displays the current users information *
 */
public class AccountDetailsController {


    /**
     * The current client being used by the GUI
     */
    Client client;

    /**
     * The current user
     */
    User currUser;

    /**
     * The label that displays the current users username
     */
    @FXML
    Label usernameLabel;
    /**
     * The button that the user can press to request a name change
     */
    @FXML
    Button changeNameButton;
    /**
     * The button that the user can press to request a password change
     */
    @FXML
    Button changePassButton;


    /**
     * Runs on page open
     */
    @FXML
    public void initialize(){

    }

    /**
     * Sets the client of this controller
     * @param client
     */
    public void setClient(Client client) {
        this.client = client;

    }

    /**
     * Sets the current user of this controller
     * @param currUser the current user
     */
    public void setUser(User currUser) {
        this.currUser = currUser;
        usernameLabel.setText(currUser.getUsername());
        System.out.println(currUser.getUsername());
    }

    /**
     * Occurs when the change name button is pressed
     * Opens the name change page
     * @throws IOException if the GUI cannot access the fxml file
     */
    @FXML
    protected void onchangeNameButtonPress() throws IOException {


        //Opens the fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("change-username-page.fxml"));

        //Opens the GUI page in regards to the fxml
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
     * Opens the page which the user cna use to change their password
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



}
