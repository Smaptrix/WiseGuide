/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   18/02/2022
    Last Updated:   11/05/2022
 */
package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.IOException;

/**
 * Controls the page which the user uses to change their username
 */
public class ChangeUsernameController {
    /**
     * The current client
     */
    Client client;
    /**
     * The current user
     */
    User currUser;

    /**
     * The label which displays the current users username
     */
    @FXML
    Label usernameLabel;

    /**
     * The text field which the user can type there desired name into
     */
    @FXML
    TextField desiredNameField;

    /**
     * The label that displays any error information
     */
    @FXML
    Label errLabel;
    /**
     * The button that ocnfirms the users request
     */
    @FXML
    Button confirmButton;

    /**
     * This function runs first on the page startup
     */
    @FXML
    public void initialize(){
        errLabel.setText("");
    }

    /**
     * Sets the current client for the controller
     * @param client the client the controller is going to work with
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Sets the current user for the controller
     * @param currUser the current user of the controller
     */
    public void setUser(User currUser){
        this.currUser = currUser;
        usernameLabel.setText(currUser.getUsername());
    }

    /**
     * Tells the client to request a name change when the button is pressed
     */
    @FXML
    protected void onConfirmButtonPress(){


        String desiredName = desiredNameField.getText();


        if(desiredName.equals(currUser.getUsername())){
            errLabel.setText("This is your current name!");
        }
        else{
            try {
                if (client.requestUserNameChange(desiredName).equals("NAMECHANGED")){

                    currUser.setUsername(desiredName);

                    Stage currStage = (Stage) confirmButton.getScene().getWindow();
                    currStage.close();

                }
                else{

                    errLabel.setText("That name is already taken!");

                }

                } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
