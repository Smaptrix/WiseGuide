package maptrix.wiseguide;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import server.ServerUserHandler;
import serverclientstuff.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class AccountCreationController {

    @FXML
    TextField userField;

    @FXML
    PasswordField passField;

    @FXML
    PasswordField passConfirmField;

    @FXML
    Label errLabel;

    @FXML
    Button createAccountButton;

    @FXML
    Button closePopUpButton;

    @FXML
    CheckBox ageCheckBox;



    //TODO - Post Integration let the client handle all the user stuff not the GUI


    @FXML
    //Attempts to create account
    //For now bypasses networking stuff while it waits for GUI and client/server integration - JI
    //Should not have to deal with the user exceptions thrown here, just the creation of the user object
    //Only here for now because we are bypassing network stuff
    private void createAccountButtonAction() throws NoSuchAlgorithmException, IOException {
        System.out.println("Wanted Username: " + userField.getText());

        System.out.println("Password: " + passField.getText());

        System.out.println("Confirm Password: " + passConfirmField.getText());

        //Big check to make sure username and password stuff is correct

        //If the password and the confrimed password dont match
        if(!(passField.getText()).equals(passConfirmField.getText())){
            errLabel.setText("The passwords do not match!");

        }

        else if(userField.getText().trim().isEmpty()){
            errLabel.setText("You have not entered a username!");
        }

        else if(passField.getText().trim().isEmpty()){
            errLabel.setText("You have not entered a password!");
        }

        else if(!ageCheckBox.isSelected()){
            errLabel.setText("You are not over the age of 13!");
        }

        else{
            errLabel.setText("");

            User newUser = new User(userField.getText(), passField.getText());
            newUser.hashUserInfo();
            ServerUserHandler desiredUser = new ServerUserHandler(newUser);

            if(desiredUser.userExistState){
                errLabel.setText("This username is taken");
            }
            else{
                desiredUser.createUser();
                if(desiredUser.userExistState){

                    FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-created-window.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 280, 155);
                    stage.setScene(scene);
                    stage.setTitle("Account Created");
                    stage.show();


                    //Close the current page
                    Stage currStage = (Stage) createAccountButton.getScene().getWindow();
                    currStage.close();

                }
            }
        }



    }


    @FXML
    //Closes popup window
    private void closePopupButton(){
        Stage stage = (Stage) closePopUpButton.getScene().getWindow();
        stage.close();
    }

}
