package maptrix.wiseguide;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Label welcomeText;

    //Imports all of the objects in the login 'scene'
    @FXML
    PasswordField userPassField;
    @FXML
    TextField userTextField;
    @FXML
    Button createAccButton;
    @FXML
    Button loginButton;
    @FXML
    Button exitButton;

    @FXML
    Label errorLabel;


    //Always called by the FXML Loader
    public void initialize(){

        errorLabel.setText("Hello :)");
    }




    @FXML
    //Closes the application
    protected void exitButtonAction(){
        System.out.println("closing down!");
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }
}