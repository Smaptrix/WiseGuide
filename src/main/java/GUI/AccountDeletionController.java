package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.*;

/**
 * <p>
 *     Controls the account deletion system within the application.
 * </p>
 */
public class AccountDeletionController {

    /**
     * <p>
     *     The current client.
     * </p>
     */
    Client client;

    /**
     * <p>
     *     Sets the client to be used by the controller.
     * </p>
     * @param client the client you want the controller to use
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * <p>
     *     Gets the current client being used by the controller.
     * </p>
     * @return the current client being used.
     */
    public Client getClient() { return this.client; }

    /**
     * <p>
     *     A boolean set to true if the deletion system is being accessed by automatic tests and may require some features to be bypassed.
     * </p>
     */
    private boolean testingMode = false; //testingMode should only be true if specifically set.

    /**
     * <p>
     *     The parent stage (Account Details Page).
     * </p>
     */
    Stage parentStage;

    /**
     * <p>
     *     The map stage.
     * </p>
     */
    Stage mapStage;

    /**
     * <p>
     *     The currently logged in user.
     * </p>
     */
    User currUser;


    /**
     * <p>
     *     The password field.
     * </p>
     */
    @FXML
    PasswordField passField;

    /**
     * <p>
     *     The password confirmation field.
     * </p>
     */
    @FXML
    PasswordField passConfirmField;

    /**
     * <p>
     *     The error label.
     * </p>
     */
    @FXML
    Label errLabel;

    /**
     * <p>
     *     The "Delete Account" button.
     * </p>
     */
    @FXML
    Button deleteAccountButton;

    /**
     * <p>
     *     The account deletion information label.
     * </p>
     */
    @FXML
    Label infoLabel;

    /**
     * <p>
     *     The password label.
     * </p>
     */
    @FXML
    Label passLabel;

    /**
     * <p>
     *     The password confirmation label.
     * </p>
     */
    @FXML
    Label passConfirmLabel;

    /**
     * <p>
     *     The "I understand account deletion is permanent" checkbox.
     * </p>
     */
    @FXML
    public CheckBox delCheckBox;

    /**
     * <p>
     *     When the user presses the delete account button, their information is verified and the account is deleted if the entered passwords are correct.
     * </p>
     * @throws IOException if the client cannot connect to the server.
     */
    @FXML
    private void deleteAccountButtonAction() throws IOException {

        //Verifies that the user input data is OK.
        if(!(passField.getText()).equals(passConfirmField.getText())){
            errLabel.setText("The passwords do not match!");
        }
        else if(passField.getText().trim().isEmpty()){
            errLabel.setText("You have not entered a password!");
        }
        else if(!delCheckBox.isSelected()){
            errLabel.setText("You must click the checkbox to continue.");
        }
        else {

            //Uses the code for the login system to determine whether the user details are valid.
            User detailsToCheck = new User(currUser.getUsername(),passField.getText().trim());
            String verificationCode = client.requestLogin(detailsToCheck);

            //If the "login" with the currently logged in username and the entered password worked, then that means
            //the password is correct and the user is valid and can therefore be deleted.
            if (verificationCode.equals("GOODLOGIN")) {

                //Close all windows and attempt to delete the user.
                Stage currStage = (Stage) deleteAccountButton.getScene().getWindow();
                currStage.close();
                //Null comparisons are made because the parentStage and mapStage will not exist during testing.
                if(parentStage != null){
                    parentStage.close();
                }
                if (mapStage != null) {
                    mapStage.close();
                }

                System.gc(); //Runs gc, allows deleteUser to update the database.
                String success = client.deleteUser(currUser);

                if (success.equals("DELETESUCCESS")){
                    accountDeletedPageOpen(false);
                } else {
                    //This should never appear. If it does, something has gone wrong in the code (probably file.delete error).
                    //Could add error codes if needed.
                    accountDeletedPageOpen(true);
                }



            } else {
                errLabel.setText("User details are incorrect.");
            }
        }
    }

    /**
     * <p>
     *     Opens a popup to display that the account has been deleted.
     * </p>
     * @param error A boolean determining whether an error occurred or not.
     * @throws IOException if the GUI cannot open the FXML file.
     */
    public void accountDeletedPageOpen(boolean error) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-deleted-window.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 280, 155);
        stage.setScene(scene);
        stage.setTitle("Account Deleted");

        AccountDeletionPopupController popupController = fxmlLoader.getController();
        popupController.setClient(client);
        popupController.setErrorMessage(error);

        stage.show();
    }

    /**
     * <p>
     *     Creates an account (For Automatic Tests that require an account to test deletion on).
     * </p>
     * @throws IOException if the client cannot connect to the server.
     */
    public void createDeletionTestAccount() throws IOException {
        User testingUser = new User("accountDeletionTestUser","accountDeletionTest");
        if(client.createUser(testingUser).equals("USERCREATED")) {
            System.out.println("ACCOUNT DELETION TEST USER was created.");
        } else {
            System.out.println("ACCOUNT DELETION TEST USER could not be created.");
        }
    }

    //Reads the list of reserved usernames to check if the user input name is allowed.
    //Redundant now that the system automatically gets the logged in user's username but keeping in code for
    //now as could be useful for testing purposes.
    private boolean forbiddenNamesCheck(String name) throws IOException {
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


    //Setters for variables used by higher other controllers.

    /**
     * <p>
     *     Activates or deactivates the controller's testing mode.
     * </p>
     * @param mode Whether testing mode should be true or false.
     */
    public void setTestingMode(boolean mode){
        this.testingMode = mode;
    }

    /**
     * <p>
     *     Sets the current user.
     * </p>
     * @param user The currently logged in user.
     */
    public void setCurrUser(User user) {
        this.currUser = user;
    }

    /**
     * <p>
     *     Sets the parent stage.
     * </p>
     * @param stage The parent stage (Account Details Page)
     */
    public void setParentStage(Stage stage) {
        this.parentStage = stage;
    }

    /**
     * <p>
     *     Sets the map stage.
     * </p>
     * @param stage The map stage.
     */
    public void setMapStage(Stage stage) {
        this.mapStage = stage;
    }

}
