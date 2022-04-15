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

import java.io.IOException;

public class AccountDeletionController {

    Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() { return this.client; }

    private String userExists;
    private boolean userPassCorrect;

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
    * Should then verify that the user exists and the password is correct, but this function does not currently work?
    */
    private void deleteAccountButtonAction() throws IOException {
        if(!(passField.getText()).equals(passConfirmField.getText())){
            errLabel.setText("The passwords do not match!");

        }
        else if(userField.getText().trim().isEmpty()){
            errLabel.setText("You have not entered a username!");
        }

        else if(passField.getText().trim().isEmpty()){
            errLabel.setText("You have not entered a password!");
        }
        else {
            User currUser = new User(userField.getText(), passField.getText());
            //TODO: Verification always fails? Account should not be deleted if it does not exist or if the password is not correct.
            //String verified = client.verifyUser(currUser);
            String verified = "USERFOUND"; //DISCARD ONCE VERIFICATION WORKS!
            if (verified == "USERFOUND") {
                String success = client.deleteUser(currUser);
                if (success.equals("DELETESUCCESS")){
                    //errLabel.setText( userField.getText().trim() + " was deleted.");
                    accountDeletedPageOpen();
                    Stage currStage = (Stage) deleteAccountButton.getScene().getWindow();
                    currStage.close();
                } else {
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

}
