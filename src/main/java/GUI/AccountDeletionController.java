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
    /*
    * Deletes Account upon button press.
    * First checks to make sure passwords match and that username and password fields are not blank.
    * Will not delete an account with a reserved username unless testingMode is on.
    * Should then verify that the user exists and the password is correct, but this function does not currently work.
    */
    private void deleteAccountButtonAction() throws IOException {
        //Verifies that the user input data is OK.
        if(!(passField.getText()).equals(passConfirmField.getText())){
            errLabel.setText("The passwords do not match!");
        }
        else if(userField.getText().trim().isEmpty()){
            errLabel.setText("You have not entered a username!");
        }
        else if(passField.getText().trim().isEmpty()){
            errLabel.setText("You have not entered a password!");
        }
        else if(forbiddenNamesCheck(userField.getText().trim()) && (!testingMode)){
            errLabel.setText("The selected user cannot be deleted.");
        }
        else {

            //Uses the login code to determine whether the user details are valid.
            User currUser = new User(userField.getText(), passField.getText());
            String verificationCode = client.requestLogin(currUser);

            if (verificationCode.equals("GOODLOGIN")) {
                String success = client.deleteUser(currUser);
                if (success.equals("DELETESUCCESS")){
                    accountDeletedPageOpen();
                    Stage currStage = (Stage) deleteAccountButton.getScene().getWindow();
                    currStage.close();
                } else {
                    //This should never appear. If it does, something has gone wrong in the code.
                    errLabel.setText("Something went wrong. The user could not be deleted.");
                }
            } else {
                errLabel.setText("User details are incorrect.");
            }
        }
    }

    public void accountDeletedPageOpen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-deleted-window.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 280, 155);
        stage.setScene(scene);
        stage.setTitle("Account Deleted");
        stage.show();
    }

    @FXML
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
    private boolean forbiddenNamesCheck(String name) throws IOException {
        boolean forbidden = false;
        File forbiddenNamesList = new File("reservedUsernames.txt");
        BufferedReader br = new BufferedReader(new FileReader(forbiddenNamesList));
        String line;
        while(((line = br.readLine()) != null) && forbidden == false){
            if(name.equals(line)){
                forbidden = true;
            }
        }
        br.close();
        return forbidden;
    }

    public void setTestingMode(boolean mode){
        this.testingMode = mode;
    }

}
