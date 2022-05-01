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
    Label deletedLabel;
    @FXML
    public CheckBox delCheckBox;

    @FXML
    /*
    * Deletes Account upon button press.
    * First checks to make sure passwords match and are not blank.
    * Then verifies that the password is correct and deletes.
    */
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
                parentStage.close();
                mapStage.close();
                System.gc(); //Runs gc, allows deleteUser to update the database.
                String success = client.deleteUser(currUser);

                if (success.equals("DELETESUCCESS")){
                    accountDeletedPageOpen();
                } else {
                    //This should never appear. If it does, something has gone wrong in the code. Could add error codes if needed.
                    accountDeletedPageOpen();
                    deletedLabel.setText("Something went wrong. ("+success+")");
                }

                //Open Login Page Here

            } else {
                errLabel.setText("User details are incorrect.");
            }
        }
    }

    //Opens the "Account Deleted!" window.
    public void accountDeletedPageOpen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-deleted-window.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 280, 155);
        stage.setScene(scene);
        stage.setTitle("Account Deleted");
        stage.show();
    }

    @FXML
    //Closes the "Account Deleted!" window.
    private void closePopupButton(){
        Stage stage = (Stage) closePopUpButton.getScene().getWindow();
        stage.close();
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
