package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    //Deletes Account
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
                    //TODO: OK POPUP
                    errLabel.setText( userField.getText().trim() + " was deleted.");
                } else {
                    errLabel.setText("Something went wrong. The user could not be deleted.");
                }
            } else {
                errLabel.setText("User details are incorrect.");
            }
        }
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
