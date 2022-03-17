package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.IOException;

public class AccountDetailsController {

    //TODO - Add password change functionality


    Client client;

    User currUser;

    @FXML
    Label usernameLabel;

    @FXML
    Button changeNameButton;

    @FXML
    Button changePassButton;




    @FXML
    public void initialize(){

    }

    public void setClient(Client client) {
        this.client = client;

    }


    public void setUser(User currUser) {
        this.currUser = currUser;
        usernameLabel.setText(currUser.getUsername());
        System.out.println(currUser.getUsername());
    }


    @FXML
    protected void onchangeNameButtonPress() throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("change-username-page.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 375);
        ChangeUsernameController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setUser(currUser);
        stage.setScene(scene);
        stage.setTitle("Change username");
        stage.show();


        //Close the current page
        Stage currStage = (Stage) changeNameButton.getScene().getWindow();
        currStage.close();





    }


    @FXML
    protected void onchangePassButtonPress() throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("change-password-page.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 375);
        ChangeUsernameController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setUser(currUser);
        stage.setScene(scene);
        stage.setTitle("Change Password");
        stage.show();


        //Close the current page
        Stage currStage = (Stage) changeNameButton.getScene().getWindow();
        currStage.close();





    }



}
