package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.IOException;

public class ChangeUsernameController {

    Client client;
    User currUser;


    @FXML
    Label usernameLabel;

    @FXML
    TextField desiredNameField;


    @FXML
    Label errLabel;

    @FXML
    Button confirmButton;


    @FXML
    public void initialize(){
        errLabel.setText("");
    }


    public void setClient(Client client) {
        this.client = client;
    }

    public void setUser(User currUser){
        this.currUser = currUser;
        usernameLabel.setText(currUser.getUsername());
    }


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
