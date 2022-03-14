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

    //TODO - Add all the functionality
    //     - I.E Display User Details - Change User details
    //     - BEFORE THIS NEED TO DO USER INFO ENCRYPTION RATHER THAN HASHING

    Client client;

    User currUser;

    @FXML
    Label usernameLabel;

    @FXML
    Button changeNameButton;




    @FXML
    public void initialize(){

    }

    public void setClient(Client client) {
        this.client = client;

    }


    public void setUser(User currUser) {
        this.currUser = currUser;
        usernameLabel.setText(currUser.getUsername());
    }


    @FXML
    protected void onchangeNamePress() throws IOException {


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


}
