package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.*;

public class AccountDeletionController {

    Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() { return this.client; }

    private String userExists;
    private boolean userPassCorrect;
    private boolean testingMode = false; //testingMode should only be true if specifically set.

    Stage parentStage;
    Stage mapStage;
    User currUser;

    @FXML
    TextField userField;
    @FXML
    PasswordField passField;
    @FXML
    PasswordField passConfirmField;
    @FXML
    Label errLabel;
    @FXML
    Button deleteAccountButton;
    @FXML
    Button closePopUpButton;
    @FXML
    Label infoLabel;
    @FXML
    public CheckBox delCheckBox;

    @FXML
    /*
     * Deletes Account upon button press.
     * First checks to make sure passwords match and are not blank.
     * Then verifies that the password is correct and deletes.
     */
    private void deleteAccountButtonAction() throws IOException {

        System.out.println("I am checking the input data.");

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

            System.out.println("I am checking the user is valid.");

            //Uses the code for the login system to determine whether the user details are valid.
            User detailsToCheck = new User(currUser.getUsername(),passField.getText().trim());
            String verificationCode = client.requestLogin(detailsToCheck);

            //If the "login" with the currently logged in username and the entered password worked, then that means
            //the password is correct and the user is valid and can therefore be deleted.
            if (verificationCode.equals("GOODLOGIN")) {

                System.out.println("The user is valid! Closing windows...");

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
                System.out.println("Deleting the user...");
                System.gc(); //Runs gc, allows deleteUser to update the database.
                String success = client.deleteUser(currUser);

                if (success.equals("DELETESUCCESS")){
                    System.out.println("The user was deleted.");
                    accountDeletedPageOpen(false);
                } else {
                    //This should never appear. If it does, something has gone wrong in the code. Could add error codes if needed.
                    System.out.println("Something really bad happened.");
                    accountDeletedPageOpen(true);
                }



            } else {
                errLabel.setText("User details are incorrect.");
            }
        }
    }

    //Opens the "Account Deleted!" window.
    public void accountDeletedPageOpen(boolean error) throws IOException {

        System.out.println("Opening the Account Deleted Page...");

        if(client == null){
            System.out.println("ADPO: The client is null! What happened?");
        } else {
            System.out.println("ADPO: Client is not null.");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-deleted-window-ok.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 280, 155);
        stage.setScene(scene);
        stage.setTitle("Account Deleted");

        AccountDeletionPopupController popupController = fxmlLoader.getController();
        popupController.setClient(client);
        popupController.setMessage(error);

        stage.show();
    }

    /*Closes the "Account Deleted!" window. -- Moved to PopupController
    @FXML
    private void closePopUpButton() throws IOException {

        System.out.println("The popup button was pressed.");
        if(client == null){
            System.out.println("CPB: The client is null! What happened?");
        } else {
            System.out.println("CPB: Client is not null.");
        }
        Stage stage = (Stage) closePopUpButton.getScene().getWindow();
        stage.close();

        reopenLogin(this.client);
    }*/

    //Reopen Login Page
    private void reopenLogin(Client client) throws IOException {
        //TODO: Client is not transferred because it randomly gets set to null at some point
        // and I can't figure out where or why this happens.
        System.out.println("Attempting to log out the client.");
        client.requestLogout();
        System.out.println("Opening the login page...");
        //Reopens the login page
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-page.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        System.out.println("Loaded login page again");
        LoginController controller = fxmlLoader.getController();
        controller.setClient(client);
        stage.setScene(scene);
        stage.setTitle("Welcome to WiseGuide");
        stage.show();
        stage.setResizable(false);
    }

    //FOR TESTING PURPOSES: CREATE AN ACCOUNT TO DELETE
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

    public void setTestingMode(boolean mode){
        this.testingMode = mode;
    }

    public void setCurrUser(User user) {
        this.currUser = user;
    }

    public void setParentStage(Stage stage) {
        this.parentStage = stage;
    }

    public void setMapStage(Stage stage) {
        this.mapStage = stage;
    }

}
