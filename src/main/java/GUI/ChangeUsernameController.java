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
import ServerClientUtility.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * <p>
 *     Controls the page which the user uses to change their username
 * </p>
 */
public class ChangeUsernameController {
    /**
     * <p>
     *     The current client
     * </p>
     */
    Client client;
    /**
     * <p>
     *    The current user
     * </p>
     */
    User currUser;

    /**
     * <p>
     *     The label which displays the current users username
     * </p>
     */
    @FXML
    Label usernameLabel;

    /**
     * <p>
     *     The label which displays the text "Current Username"
     * </p>
     */
    @FXML
    Label usernameText;

    /**
     * <p>
     *     The label which displays the text "Desired Username"
     * </p>
     */
    @FXML
    Label desiredText;

    /**
     * <p>
     *     The text field which the user can type there desired name into
     * </p>
     */
    @FXML
    TextField desiredNameField;

    /**
     * <p>
     *     The label that displays any error information
     * </p>
     */
    @FXML
    Label errLabel;
    /**
     * <p>
     *     The button that confirms the users request
     * </p>
     */
    @FXML
    Button confirmButton;

    /**
     * <p>
     *     The title
     * </p>
     */
    @FXML
    Label title;

    /**
     * <p>
     *     Whether testing mode is enabled.
     * </p>
     */
    boolean testingMode;

    /**
     * <p>
     *     This function runs first on the page startup
     * </p>
     */
    @FXML
    public void initialize(){
        errLabel.setText("");
    }

    /**
     * <p>
     *     Sets the current client for the controller
     * </p>
     * @param client the client the controller is going to work with
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * <p>
     *     Sets the current user for the controller
     * </p>
     * @param currUser the current user of the controller
     */
    public void setUser(User currUser){
        this.currUser = currUser;
        usernameLabel.setText(currUser.getUsername());
    }

    /**
     * <p>
     *     Gets the current user for the controller
     * </p>
     * @return The current user
     */
    public User getUser(){
        return currUser;
    }

    public void setTestingMode(boolean testingMode) { this.testingMode = testingMode; }

    /**
     * <p>
     *     Tells the client to request a name change when the button is pressed
     * </p>
     */
    @FXML
    protected void onConfirmButtonPress() throws IOException {


        String desiredName = desiredNameField.getText();

        if(forbiddenNameCheck(desiredName) && !testingMode) {
            errLabel.setText("That username is not allowed.");
        } else if(desiredName.equals(currUser.getUsername())){
            errLabel.setText("This is your current name!");
        } else if(desiredName.equals("")){
            errLabel.setText("You cannot have a blank name!");
        } else{
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

    /**
     * <p>
     *     Checks to see if the desired username is allowed.
     * </p>
     * @return whether the username is allowed.
     */
    private boolean forbiddenNameCheck(String name) throws IOException {
        boolean forbidden = false;
        File forbiddenNamesList = new File("reservedUsernames.txt");
        BufferedReader br = new BufferedReader(new FileReader(forbiddenNamesList));
        String line;
        while(((line = br.readLine()) != null) && !forbidden){
            if(name.equals(line)){
                forbidden = true;
            }
        }
        br.close();
        return forbidden;
    }


}
